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
        type="external_profile"
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
      <template v-slot:item.role_ids="{item}">
        <VChipGroup column="false">
          <VChip v-for="role in item.role_ids" :key="role.name" size="small" variant="outlined">
            {{ role.name }}
          </VChip>
        </VChipGroup disabled>
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
  role_ids: Array<{
    name: string,
  }>,
}

export default defineComponent({

  data() {
    return {
      items: [] as Item[],
      search: '',
      headers: [
        {title: 'ID', key: 'id'},
        {title: 'Período académico', key: 'academic_period.name'},
        {title: 'Nombre', key: 'user.full_name'},
        {title: 'Rol', key: 'role_ids'},
        {title: 'Número de identificación', key: 'user.identification_number'},
        {title: 'Código de usuario', key: 'user.user_code'},
        {title: 'Correo electrónico', key: 'user.email'},
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



