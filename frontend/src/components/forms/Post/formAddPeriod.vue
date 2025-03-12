<template>
  <VForm validate-on="submit" @submit.prevent="addUser">
    <VTextField label="Nombre" name="name" id="name" v-model="item.name"/>
    <VDateInput label="Fecha de inicio" prepend-inner-icon="ri-calendar-start" prepend-icon="" type="text" name="startDate" id="startDate"  v-model="item.start_date"/>
    <VDateInput label="Fecha de finzalización" prepend-inner-icon="ri-calendar-end" prepend-icon="" type="text" name="endtDate" id="endtDate"  v-model="item.end_date"/>
    <VRadioGroup v-model="item.is_active" class="d-flex" inline>
      <VRadio label="Activo" :value="true"/>
      <VRadio label="Inactivo" :value="false"/>
    </VRadioGroup>
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
    name: string,
    start_date: string,
    end_date: string,
    is_active: boolean,
  }

  export default defineComponent({
    name: 'formAddPeriod',
    data() {
      return {
        item: {} as Item,
        loading:false
      }
  },
  methods: {
    addUser() {
      this.loading=true
      API.post(API.POST_ASSESMENT_PERIOD, this.item)
        .then((data) => {
          console.log(this.item)
          this.$router.push('/periodos');
        })
        .catch((error) => {
          this.loading=false
          console.error('Error al realizar la solicitud:', error);
        });
    }
  }
});
</script>
