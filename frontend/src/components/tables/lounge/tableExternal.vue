<script lang="ts">
import { defineComponent } from "vue"

//utils
import API from "@/utils/api";



interface Item {
  id: number,
  name: string
  userCode: string,
  identificationNumber: string,
  email: string,
  sex: string,

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
        {title: 'Correo', key: 'email'},
        {title: 'Sexo', key: 'sex'},
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
        this.items = await API.get(API.GET_EXTERNAL_FUNCTIONARY_PROFILE_BY_SEEDBED_ID + this.$route.params.idSemillero);
        this.$emit('loaded')
      } catch (error) {
        console.error('Error fetching users:', error);
      }
    },
  },
})
</script>

<template>
  <VCard flat>
    <h2>Aliados externos</h2>
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
      <VBtn to="addPeriod" class="mx-2" prepend-icon="ri-add-fill"> Agregar</VBtn>
    </VCardTitle>
    <VDataTable
      :items="items"
      :search="search"
      :headers="headers"
    >


    </VDataTable>
  </VCard>
</template>
