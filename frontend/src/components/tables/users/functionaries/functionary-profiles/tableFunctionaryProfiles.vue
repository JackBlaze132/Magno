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
      <QuickActions
      toCreate
      type="functionary_profile"
      @itemCreated="handleItemRefresh"
    />
    </VCardTitle>
    <VDataTable
      :items="items"
      :search="search"
      :headers="headers"
    >
      <template v-slot:item.is_external_user="{item}">
        {{ externalFormatter(item.is_external_user)}}
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
  user: {
    full_name: string,
    identification_number: string,
    user_code: string,
    email: string,
  },
  academic_period: {
    name: string,
  },
  dependency: {
    name: string,
  },
}

export default defineComponent({

  data() {
    return {
      items: [] as Item[],
      search: '',
      headers: [
        {title: 'ID', key: 'id'},
        {title: 'Nombre', key: 'user.full_name'},
        {title: 'Número de identificación', key: 'user.identification_number'},
        {title: 'Código de usuario', key: 'user.user_code'},
        {title: 'Correo electrónico', key: 'user.email'},
        {title: 'Período académico', key: 'academic_period.name'},
        {title: 'Dependencia', key: 'dependency.name'},
      ]
    }
  },
  // ...
  created() {
    this.getUsers();
    //this.externalFormatter();
  },
  methods: {
    async getUsers() {
      const headers={
        'API-VERSION': '1',
      }
      try {
        this.items = await API.get(API.FUNCTIONARY_PROFILES_ASSIGNED + this.$route.params.idFunctionary, headers);
        this.$emit('loaded');
      } catch (error) {
        console.error('Error fetching users:', error);
      }
    },
    externalFormatter(state: boolean){
      return Formatter.externalFormatter(state)
    },
    handleItemRefresh(){
      this.getUsers();
    }
  },
})


</script>



