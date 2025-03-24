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
      <QuickActions
      toCreate
      type="grupo"
      @itemCreated="handleItemRefresh"
    />
    </VCardTitle>
    <VDataTable
      :items="items"
      :search="search"
      :headers="headers"
    >
      <!--<template v-slot:item.isActive="{item}">
        <VChip :color="item.isActive ? 'green' : ''" >
          {{ periodActivityFormatter(item.isActive)}}
        </VChip>
      </template>-->

      <template v-slot:item.link="{item}">
        <QuickActions
          toEdit
          toDelete
          type="grupo"
          :name="item.name"
          :index="item.id"
          :initialData="setInitialData(item)"
          @itemDeleted="handleItemRefresh"
          @itemEdited="handleItemRefresh"
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
  lines_of_research: Array<string>,
}

export default defineComponent({

  emits: ['loaded'],
  data() {
    return {
      items: [] as Item[],
      search: '',
      links: '',
      headers: [
        {title: 'ID', key: 'id'},
        {title: 'Nombre', key: 'name'},
        {title: 'Lineas de investigaión', key: 'lines_of_research'},
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
      const apiHeaders = {
        'API-VERSION': '1',
      }
      try {
        this.items = await API.get(API.GET_INVESTIGATION_GROUPS, apiHeaders);
        this.$emit('loaded');
      } catch (error) {
        console.error('Error fetching users:', error);
      }
    },
    handleItemRefresh() {
      this.getGroups();
    },
    setInitialData(item: any) {
      return {
        name: item.name,
        lines_of_research: item.lines_of_research,
      }
    }
  },
})
</script>
