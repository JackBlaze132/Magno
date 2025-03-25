<template>
  <VForm validate-on="submit" @submit.prevent="addGroup">
    <VTextField label="Nombre" name="name" id="name" v-model="item.name"/>
    <VSelect label="Director" :items="functionaries" item-title="name" item-value="id" v-model="item.coordinator_fp_id"></VSelect>

    <LoadingBtn
      text="Guardar"
      icon="ri-save-2-line"
      :loading="loading"
    />
  </VForm>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import API from "@/utils/api";
import { VSelect } from 'vuetify/components';
import LoadingBtn from '@/components/loadingBtn.vue';


interface Item {
  name: string,
  coordinator_fp_id: string
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
      selectedFunctionary: null,
      loading:false
    }
  },
  created() {
      this.getFunctionaries();
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
    addGroup() {
      this.loading=true
      API.post(API.POST_INVESTIGATION_GROUP, this.item)
      .then((data) => {
        this.$router.push('listar-grupos');
      })
      .catch((error) => {
        this.loading=false
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


