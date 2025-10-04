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
      type="seedbed"
      @itemCreated="handleItemRefresh"
    />
    </VCardTitle>
    <VDataTable
      :items="items"
      :search="search"
      :headers="headers"
    >
    <template v-slot:item.line_of_research="{item}">
        <VChip>
          {{item.line_of_research}}
        </VChip>
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
import QuickControl from "@/components/quickControl.vue";

interface Item {
  id: number,
  name: string,
  mission: string,
  vision: string,
  research_proposal_description:string
  creation_date: date,
  line_of_research: string
}

export default defineComponent({
  components: {QuickControl},

  data() {
    return {
      items: [] as Item[],
      line: [] as string[],
      search: '',
      headers: [
        {title: 'ID', key: 'id'},
        {title: 'Nombre', key: 'name'},
        {title: 'Linea de investigación', key: 'line_of_research'},
        { key: 'link', sortable: false},
      ]
    }
  },
  // ...
  created() {
    this.getSeedbeds();
    //this.externalFormatter();
  },
  methods: {
    async getSeedbeds() {
      const headers={
        'API-VERSION': '1',
      }
      try {
        const seedbeds = await API.get(API.RESEARCH_SEEDBEDS, headers);

        for (const seedbed of seedbeds) {
        const lineOfResearch = await API.get(
          API.LINES_OF_RESEARCH_BY_RESEARCH_SEEDBED + seedbed.id,
          headers
        )
        seedbed.line_of_research = lineOfResearch;
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
      this.getSeedbeds();
    },
    setInitialData(item: any) {
      return {
        name: item.name,
        mission: item.mission,
        vision: item.vision,
        research_proposal_description: item.research_proposal_description,
        creation_date: item.creation_date,
        line_of_research: item.line_of_research,
      }
    }
  },
})


</script>



