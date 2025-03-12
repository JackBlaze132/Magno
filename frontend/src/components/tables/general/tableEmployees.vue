<script lang="ts">
import { defineComponent } from "vue"

//utils
import API from "@/utils/api";
import Formatter from "@/utils/formatter";

interface Item {
  id: number,
  userIdentification: string,
  email: string,
  isExternalUser: boolean,
}

export default defineComponent({

  data() {
    return {
      items: [] as Item[],
      search: '',
      headers: [
        {title: 'ID', key: 'id'},
        {title: 'Número de identificación', key: 'userIdentification'},
        {title: 'Correo electrónico', key: 'email'},
        {title: 'Afiliación', key: 'isExternalUser'}
      ]
    }
  },
  // ...
  created() {
    this.getUsers();
  },
  methods: {
    async getUsers() {
      try {
        this.items = await API.get('getFunctionaryProfiles');
        this.$emit('loaded');
      } catch (error) {
        console.error('Error fetching users:', error);
      }
    },
    externalFormatter(state:boolean){
      return Formatter.externalFormatter(state)
    }
  },
})


</script>

<template>
  <VCard flat>
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
      <VBtn to="agregar-usuarios" class="mx-2" prepend-icon="ri-add-fill"> Agregar</VBtn>
    </VCardTitle>
    <VDataTable
      :items="items"
      :search="search"
      :headers="headers"
    >
      <template v-slot:item.isExternalUser="{item}">
        {{ externalFormatter(item.isExternalUser)}}

      </template>
    </VDataTable>
  </VCard>
</template>

