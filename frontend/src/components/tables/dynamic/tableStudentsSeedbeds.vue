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
    </VCardTitle>
    <VDataTable
      :items="items"
      :search="search"
      :headers="headers"
    >
    <template v-slot:item.assesmentPeriod="{item}">
      <VChip v-if="item.assesmentPeriod">
        {{ item.assesmentPeriod.name }}
      </VChip>
    </template>

    <template v-slot:item.link="{item}">
      <QuickActions
        :toView="`/periodos/${item.assesmentPeriod.id}/grupos-investigacion/${item.investigationGroup.id}/semilleros/${item.id}`"
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
  userIdentification: string,
  email: string,
  isExternalUser: boolean,
}

export default defineComponent({

  data() {
    return {
      items: [] as Item[],
      search: '',
      headers: [
        {title: 'ID', key: 'id'},
        {title: 'Periodo', key: 'assesmentPeriod'},
        {title: 'Grupo', key: 'investigationGroup.name'},
        {title: 'Nombre', key: 'name'},
        { key: 'link', sortable: false},
      ],
    }
  },
  // ...
  created() {
    this.getUsers();
  },
  methods: {
    async getUsers() {
      try {
        this.items = await API.get(`${API.GET_STUDENT_SEEDBEDS}${this.$route.params.idNumber}`);
        this.$emit('loaded');
      } catch (error) {
        console.error('Error fetching users:', error);
      }
    },
    externalFormatter(state:boolean){
      return Formatter.externalFormatter(state)
    }
  },
})

</script>

