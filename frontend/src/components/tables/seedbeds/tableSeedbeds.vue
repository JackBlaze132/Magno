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
      type="seedbed"
      @itemCreated="handleItemRefresh"
    />
    </VCardTitle>
    <VDataTable
      :items="items"
      :search="search"
      :headers="headers"
    >
    <template v-slot:item.line_of_research>
      <VChipGroup>
        <VChip>
          {{line}}
        </VChip>
      </VChipGroup>
    </template>

    <template v-slot:item.link="{item}">
      <QuickActions
        toEdit
        toDelete
        type="seedbed"
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
import Formatter from "@/utils/formatter";

interface Item {
  id: number,
  name: string,
  line_of_research: string
}

export default defineComponent({

  data() {
    return {
      items: [] as Item[],
      line: '',
      search: '',
      headers: [
        {title: 'ID', key: 'id'},
        {title: 'Nombre', key: 'name'},
        {title: 'Lineas de investigación', key: 'line_of_research'},
        { key: 'link', sortable: false},
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
        const seedbeds = await API.get(API.RESEARCH_SEEDBEDS, headers);

        for (const seedbed of seedbeds) {
        const lineOfResearch = await API.get(
          API.LINES_OF_RESEARCH_BY_RESEARCH_SEEDVBED + seedbed.id,
          headers
        )
        this.line = lineOfResearch;
        }
        this.items = seedbeds;
        this.$emit('loaded');
        //return this.lines;
      } catch (error) {
        console.error('Error fetching users:', error);
      }
    },
    externalFormatter(state: boolean){
      return Formatter.externalFormatter(state)
    },
    handleItemRefresh(){
      this.getUsers();
    },
    setInitialData(item: any) {
      return {
        name: item.name,
        line_of_research: item.line_of_research,
      }
    }
  },
})


</script>



