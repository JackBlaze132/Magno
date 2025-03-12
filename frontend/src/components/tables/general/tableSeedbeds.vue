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
      <VBtn to="addPeriod" class="mx-2" prepend-icon="ri-add-fill"> Agregar</VBtn>
    </VCardTitle>
    <VDataTable
      :items="items"
      :search="search"
      :headers="headers"
      :sort-by="sortBy"
    >
    <template v-slot:item.active="{item}">
      <VChip :color="item.active ? 'green' : ''" >
        {{ periodActivityFormatter(item.active)}}
      </VChip>
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
  name: string,
  active: boolean,
  coordinator: {
    name: string
  },
  assesmentPeriod:{
    name: string
  }
}

export default defineComponent({

  data() {
    return {
      items: [] as Item[],
      search: '',
      links: '',
      sortBy:[{key:'assesmentPeriod.name', order:'des'}, {key: 'name', order:'asc'}],
      headers: [
        {title: 'ID', key: 'id'},
        {title: 'Nombre', key: 'name'},
        {title: 'Periodo', key: 'assesmentPeriod.name'},
        {title: 'Coordinador', key: 'coordinator.name'},
        {title: 'Estado', key: 'active'}
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
        this.items = await API.get(API.GET_RESEARCH_SEEDBEDS);
        this.$emit('loaded');
      } catch (error) {
        console.error('Error fetching users:', error);
      }
    },
    periodActivityFormatter(state:boolean){
      return Formatter.periodActivityFormatter(state);
    }
  },
})
</script>
