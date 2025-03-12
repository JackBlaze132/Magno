

<template>
  <VForm validate-on="submit" @submit.prevent="addSeedbed">
    <VSelect :label="label" :items="functionaries" item-title="name" item-value="id" v-model="item.new_functionary_fp_id"></VSelect>

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
import LoadingBtn from '../loadingBtn.vue';


interface Item {
  research_seedbed_id: string,
  new_functionary_fp_id: string,
  role:string

}

interface Functionary{
  id: number,
  name: string
}

export default defineComponent({
  name: 'formAddPeriod',
  props:{
    roler:{
      type: String,
      default: '',
    },
    label:{
      type: String,
    }
  },
  data() {
    return {
      item: {} as Item,
      functionaries: [] as Functionary[],
      selectedFunctionary: null,
      loading: false
    }
  },
  created() {
      this.getFunctionaries();
      this.item.research_seedbed_id = this.$route.params.idSemillero.toString();
      this.item.role = this.roler?.toString()

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
      this.loading=true
      API.patch(API.PATCH_RESEARCH_SEEDBED_FUNCTIONARY, this.item)
      .then((data) => {
        if (data.error) {
          this.loading = false
          console.error("Error al realizar la solicitud", data.error);
        } else {
          console.log(data);
          this.$router.push('detalles');
        }
      })
      .catch((error) => {
        this.loading = false
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
