<template>
  <VForm validate-on="submit" @submit.prevent="updateData">
    <VTextField label="Nombre" name="name" id="name" v-model="item.name"/>
    <VDateInput label="Fecha de inicio" prepend-inner-icon="ri-calendar-start" prepend-icon="" type="text" name="startDate" id="startDate" v-model="item.start_date"/>
    <VDateInput label="Fecha de finzalización" prepend-inner-icon="ri-calendar-end" prepend-icon="" type="text" name="endtDate" id="endtDate" v-model="item.end_date"/>
    <LoadingBtn text="Guardar" icon="ri-save-2-line" :loading="loading"/>
  </VForm>

</template>

<script lang="ts">
import { defineComponent } from 'vue'
import API from "@/utils/api";
import LoadingBtn from '../loadingBtn.vue';


interface Item {
  id: string,
  name: string,
  start_date: string,
  end_date: string,

}


export default defineComponent({
  name: 'formUpdatePeriod',
  props:{
    label:{
      type: String,
    }
  },
  data() {
    return {
      item: {} as Item,
      loading: false,
      selectedFunctionary: null
    }
  },
  created() {
      this.item.id = this.$route.params.idPeriodo.toString();
  },

  methods: {
    updateData() {
      this.loading = true;
      API.patch(API.PATCH_ASSESMENT_PERIOD, this.item)
      .then((data) => {
        if (data.error) {
          console.error("Error al realizar la solicitud", data.error);
          this.loading = false
        } else {
          console.log(data);
          this.$router.push({name: 'listar-periodos'});
        }
      })
      .catch((error) => {
        console.error('Error al realizar la solicitud:', error);
        this.loading = false
      });
    }
  },
});
</script>
