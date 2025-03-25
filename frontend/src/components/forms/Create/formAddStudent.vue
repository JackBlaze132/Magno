<template>
  <VForm validate-on="submit" @submit.prevent="addSeedbed">
    <VTextField label="Número de identificación" name="name" id="name" v-model="item.identification_number"/>
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
  research_seedbed_id: string
}

export default defineComponent({
  name: 'formAddPeriod',
  data() {
    return {
      item: {} as Item,
      selectedFunctionary: null,
      loading: false
    }
  },
  created() {
      this.item.research_seedbed_id = this.$route.params.idSemillero.toString()
  },

  methods: {
    addSeedbed() {
      this.loading=true
      API.post(API.POST_STUDENT_PROFILE, this.item)
      .then((data) => {
        if (data.error) {
          this.loading=false
          console.error("Error al realizar la solicitud", data.error);
        } else {
          console.log(data);
          this.$router.push('detalles');
        }
      })
      .catch((error) => {
        this.loading=false
        console.error('Error al realizar la solicitud:', error);
      });
    }
  },
});
</script>
