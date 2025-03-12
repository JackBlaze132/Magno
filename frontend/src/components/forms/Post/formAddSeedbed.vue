<script lang="ts">
import { defineComponent } from 'vue'
import API from "@/utils/api";
import { VSelect } from 'vuetify/components';


interface Item {
  name: string,
  coordinator_fp_id: string,
  investigation_group_id: string,
  tutor_fp_id: string
}

interface Functionary{
  id: number,
  name: string
}

export default defineComponent({
  name: 'formAddPeriod',
  data() {
    return {
      item: {} as Item,
      functionaries: [] as Functionary[],
      selectedFunctionary: null
    }
  },
  created() {
      this.getFunctionaries();
      this.item.investigation_group_id = this.$route.params.idGrupo.toString()
  },

  methods: {
    async getFunctionaries() {
      try {
        this.functionaries = await API.get(API.GET_FUNNCTIONARY_PROFILES_BY_ASSESMENT_PERIOD_ID + this.$route.params.idPeriodo);
        this.$emit('loaded');
        console.log("Hola obtuve los funcionarios")
      } catch (error) {
        console.error('Error fetching users:', error);
        }
    },
    addSeedbed() {
      API.post(API.POST_RESEARCH_SEEDBED, this.item)
      .then((data) => {
        if (data.error) {
          console.error("Error al realizar la solicitud", data.error);
        } else {
          console.log(data);
          this.$router.push('listar-semilleros');
        }
      })
      .catch((error) => {
        console.error('Error al realizar la solicitud:', error);
      });
    }
  },
  computed: {
    functionaryId() {
      return this.functionaries.map(functionary => functionary.id);
    },
  }
});
</script>

<template>
  <VForm validate-on="submit" @submit.prevent="addSeedbed">
    <VTextField label="Nombre" name="name" id="name" v-model="item.name"/>
    <VSelect label="Coordinador" :items="functionaries" item-title="name" item-value="id" v-model="item.coordinator_fp_id"></VSelect>
    <VSelect label="Tutor" :items="functionaries" item-title="name" item-value="id" v-model="item.tutor_fp_id"></VSelect>

    <VBtn
      text="Guardar"
      prepend-icon="ri-save-2-line"
      type="submit"
    />
  </VForm>
</template>
