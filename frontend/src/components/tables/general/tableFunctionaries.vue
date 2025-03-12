<template>
  <VCard flat class="pa-5 my-3">
    <VCardTitle class="d-flex align-center justify-end">
      <VTextField
        v-model="search"
        density="compact"
        label="Search"
        prepend-inner-icon="ri-search-line"
        variant="outlined"
        hide-details
        single-line
      ></VTextField>
      <VBtn to="agregar-funcionarios" class="mx-2" prepend-icon="ri-add-fill"> Agregar</VBtn>
    </VCardTitle>
    <VDataTable
      :items="items"
      :search="search"
      :headers="headers"
    >
    <template v-slot:item.assesmentPeriod="{item}">
      <VChip v-if="item.assesmentPeriod">
        {{ item.assesmentPeriod.name }}
      </VChip>
    </template>
    </VDataTable>
  </VCard>
</template>

<script lang="ts">
import { defineComponent } from "vue"

//utils
import API from "@/utils/api";



export default defineComponent({

  data() {
    return {
      items: [{}],
      search: '',
      headers: [
        {title: 'ID', key: 'id'},
        {title: 'Nombre', key: 'name'},
        {title: 'Código', key: 'userCode'},
        {title: 'Periodo', key: 'assesmentPeriod'},
        {title: 'Identificación', key: 'identificationNumber'},
        {title: 'Teléfono', key: 'phoneNumber'},
        {title: 'Correo', key: 'email'},
        {title: 'Sexo', key: 'sex'},
        {title: 'Dependecia', key: 'dependency.name'},
        { key: 'link', sortable: false},
      ],
    }
  },
  // ...
  created() {
    this.getUsers();
  },
  methods: {
    async getUsers() {
      try {
        this.items = await API.get(API.GET_FUNCTIONARY_PROFILES);
        console.log(this.items);
        this.$emit('loaded');
      } catch (error) {
        console.error('Error fetching users:', error);
      }
    },
  },
})


</script>

