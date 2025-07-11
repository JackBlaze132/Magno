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
      type="group_profile"
      @itemCreated="handleItemRefresh"
    />
    </VCardTitle>
    <VDataTable
      :items="items"
      :search="search"
      :headers="headers"
    >
      <template v-slot:item.lines_of_research="{item}">
        <div style="max-width: 500px">
        <VChipGroup>
          <VChip v-for="(line, index) in lines[item.id]" :key="index">
            {{line}}
          </VChip>
        </VChipGroup>
        </div>
      </template>

      <template v-slot:item.link="{item}">
        <QuickActions
          toEdit
          toDelete
          :toView="item.id + '/semilleros'"
          type="group_profile"
          :name="item.investigation_group.name"
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
  investigation_group:{
    name: string
    lines_of_research: Array<string>,
  },
  coordinator:{
    user: {
      full_name: string
    }
  }

}

export default defineComponent({

  emits: ['loaded'],
  data() {
    return {
      items: [] as Item[],
      lines: [] as Array<string>,
      search: '',
      links: '',
      headers: [
        {title: 'ID', key: 'id'},
        {title: 'Nombre', key: 'investigation_group.name'},
        {title: 'Director', key: 'coordinator.user.full_name'},
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
        const groups = await API.get(API.INVESTIGATION_GROUPS_PROFILES_BY_ACADEMIC_PERIOD + this.$route.params.idPeriodo, apiHeaders);

        for (const group of groups) {
        const linesOfResearch = await API.get(
          API.LINES_OF_RESEARCH_BY_INVESTIGATION_GROUP + group.investigation_group.id,
          apiHeaders
        )
        this.lines[group.id] = linesOfResearch;
        }
        this.items = groups;
        this.$emit('loaded');
        //return this.lines;
      } catch (error) {
        console.error('Error fetching users:', error);
      }
    },

    handleItemRefresh() {
      this.getGroups();
    },
    setInitialData(item: any) {
      return {
        investigation_group_id: item.investigation_group.id,
        coordinator_id: item.coordinator.user.id,
        academic_period_id: this.$route.params.idPeriodo,
      }
    }
  },
})
</script>
