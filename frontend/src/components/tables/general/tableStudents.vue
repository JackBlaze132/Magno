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

    </VCardTitle>
    <VDataTable
      :items="items"
      :search="search"
      :headers="headers"
    >
      <template v-slot:item.userStudent.isExternalUser="{item}">
        {{ externalFormatter(item.userStudent.isExternalUser)}}

      </template>

      <template v-slot:item.link="{item}">
        <QuickActions
          :toView="item.identificationNumber + '/detalles-estudiante'"
          ></QuickActions>

      </template>
    </VDataTable>
  </VCard>
</template>

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
        {title: 'Nombre', key: 'name'},
        {title: 'Código', key: 'userCode'},
        {title: 'Identificación', key: 'identificationNumber'},
        {title: 'Semestre', key: 'semester'},
        {title: 'Teléfono', key: 'phoneNumber'},
        {title: 'Correo', key: 'email'},
        {title: 'Sexo', key: 'sex'},
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
        this.items = await API.get(API.GET_STUDENT_PROFILES);
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

