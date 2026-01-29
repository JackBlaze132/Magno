<template>
  <h1>Grupos de investigación</h1>
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
      <VBtn to="agregar-grupo" class="mx-2" prepend-icon="ri-add-fill"> Agregar</VBtn>
      <QuickControl
        toRefresh
        type="group_profile"
        @refresh="handleItemRefresh"
      />
    </VCardTitle>
    <VDataTable
      :items="items"
      :search="search"
      :headers="headers"
    >
      <!--<template v-slot:item.isActive="{item}">
        <VChip :color="item.isActive ? 'green' : ''" >
          {{ SeedbedStatus(item.isActive)}}
        </VChip>
      </template>-->

      <template v-slot:item.link="{item}">
        <QuickActions
          :toView="item.id + '/semilleros'"
          :toEdit="item.id + '/editar-grupo'"
          :toDelete="item.id"
          :deleteItem="item.name"
          deleteType="grupo"
          @itemDeleted="handleItemDeleted"
          ></QuickActions>
      </template>
    </VDataTable>
  </VCard>
</template>

<script lang="ts">
import { defineComponent } from "vue"

//utils
import API from "@/utils/api";
//import Formatter from "@/utils/formatter";


interface Item {
  id: number,
  name: string,
  coordninator: {
    name: string
  }
}

export default defineComponent({

  data() {
    return {
      items: [] as Item[],
      search: '',
      links: '',
      headers: [
        {title: 'ID', key: 'id'},
        {title: 'Nombre', key: 'name'},
        {title: 'Director', key: 'coordinator.name'},
        { key: 'link', sortable: false},
      ],
    }
  },
  // ...
  created() {
    this.getGroups();
  },
  methods: {
    async getGroups() {
      const headers = {
        'API-VERSION': '1 '
      }
      try {
        this.items = await API.get(API.INVESTIGATION_GROUPS_PROFILES_BY_ACADEMIC_PERIOD + this.$route.params.idPeriodo, headers);
        this.$emit('loaded');
      } catch (error) {
        console.error('Error fetching users:', error);
      }
    },
    handleItemDeleted(indes:number){
      this.items.splice(indes, 1);
      this.getGroups();
    },
    handleItemRefresh(){
      this.getGroups();
    }
  },
})
</script>
