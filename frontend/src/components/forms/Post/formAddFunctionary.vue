<template>
  <VForm validate-on="submit" @submit.prevent="addItem">
    <VTextField label="Número de identificación" name="name" id="name" v-model="item.identification_number"/>
    <VSelect
      label="Periodo Académico"
      :items="periods"
      item-title="name"
      item-value="id"
      v-model="item.assesment_period_id"
    />
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
import LoadingBtn from '../loadingBtn.vue';

interface Item {
  identification_number: string,
  assesment_period_id: string
}

interface Period {
  id: string,
  name: string
}

export default defineComponent({
  name: 'formAddFunctionary',
  data() {
    return {
      item: {} as Item,
      periods: [] as Period[],
      loading: false
    }
  },
  created() {
    this.getPeriods();
  },
  methods: {
    async getPeriods() {
      try {
        this.periods = await API.get(API.GET_ASSESMENT_PERIODS);
      } catch (error) {
        console.error('Error fetching periods:', error);
      }
    },
    addItem() {
      this.loading = true;
      API.post(API.POST_FUNCTIONARY_PROFILE, this.item)
        .then((data) => {
          this.$router.push('/funcionarios');
        })
        .catch((error) => {
          console.error('Error al realizar la solicitud:', error);
        })
        .finally(() => {
          this.loading = false;
        });
    }
  },
});
</script>
