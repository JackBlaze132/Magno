<script lang="ts">
import { defineComponent } from "vue"

//utils
import API from "@/utils/api";

interface Item {
  id: number,
  name: string
  userCode: string,
  identificationNumber: string,
  phoneNumber: string,
  email: string,
  sex: string,
  dependency:{
    name:string,
  }

}

export default defineComponent({

  data() {
    return {
      items: [] as Item[],
      search: '',
      links: '',
      loaded: false,
      headers: [
        {title: 'ID', key: 'id'},
        {title: 'Nombre', key: 'name'},
        {title: 'Código', key: 'userCode'},
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
    this.getSeedBeds();
  },
  methods: {
    async getSeedBeds() {
      try {
        this.items = await API.get(API.GET_COORDINATOR_BY_RESEARCH_SEEDBED_ID + this.$route.params.idSemillero);
        this.$emit('loaded');
      } catch (error) {
        console.error('Error fetching users:', error);
      }
    },
  },
})
</script>

<template>
  <VCard flat>
    <h2>Coordinador</h2>
    <VCardTitle class="d-flex align-center justify-end">
      <VBtn to="editar-coordinador" class="mx-2" prepend-icon="ri-pencil-fill">Editar</VBtn>
    </VCardTitle>
    <VDataTable
      :items="items"
      :search="search"
      :headers="headers"
    >

    </VDataTable>
  </VCard>
</template>
