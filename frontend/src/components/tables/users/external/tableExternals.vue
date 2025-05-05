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
      type="user_integra"
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
      <template v-slot:item.link="{item}">
        <QuickActions
          :toView="item.id + '/grupos-investigacion'"
        />
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
  full_name: string,
  identification_number: string,
  user_code: string,
  email: string,
  is_external_user: boolean,
  sex: string,
}

export default defineComponent({

  data() {
    return {
      items: [] as Item[],
      search: '',
      headers: [
        {title: 'ID', key: 'id'},
        {title: 'Nombre', key: 'full_name'},
        {title: 'Número de identificación', key: 'identification_number'},
        {title: 'Código de usuario', key: 'user_code'},
        {title: 'Correo electrónico', key: 'email'},
        {title: 'Sexo', key: 'sex'},
        {title: 'Afiliación', key: 'is_external_user'},
        {key: 'link', sortable: false},

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
        this.items = await API.get(API.USERS_EXTERNAL, headers);
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



