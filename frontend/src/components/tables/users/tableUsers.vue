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
      <QuickControl
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
      <template v-slot:item.type_of_internal_user="{item}">
        <VChip variant="outlined" >
          {{ formatter().snakeCaseToNaturalTitleCase(item.type_of_internal_user) || ALIADO}}
        </VChip>
      </template>
      <template v-slot:item.is_external_user="{item}">
        {{ null || formatter().externalFormatter(item.is_external_user)}}
      </template>
      <template v-slot:no-data>
        <div class="text-center pa-4">
          <p>No hay datos disponibles</p>
        </div>
      </template>
    </VDataTable>
  </VCard>
</template>

<script lang="ts">
import { defineComponent } from "vue"

//utils
import API from "@/utils/api";
import Formatter from "@/utils/formatter";
import QuickControl from "@/components/operators/quickControl.vue";

export default defineComponent({
  components: {QuickControl},

  data() {
    return {
      search: '',
      items: [] as Array<Item>,
      headers: [
        {title: 'ID', key: 'id'},
        {title: 'Nombre', key: 'full_name'},
        {title: 'Tipo', key:  'type_of_internal_user'},
        {title: 'Número de identificación', key: 'identification_number'},
        {title: 'Código de usuario', key: 'user_code'},
        {title: 'Correo electrónico', key: 'email'},
        {title: 'Sexo', key: 'sex'},
        {title: 'Afiliación', key: 'is_external_user'}
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
        this.items = await API.get(API.USERS_INTERNAL, headers);
        this.$emit('loaded');
      } catch (error) {
        console.error('Error fetching users:', error);
      }
    },

    formatter(){
      return Formatter
    },

    handleItemRefresh(){
      this.getUsers();
    }
  },
})


</script>
