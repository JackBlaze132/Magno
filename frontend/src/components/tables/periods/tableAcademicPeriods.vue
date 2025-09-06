<template>
  <h1>Periodos académicos</h1>
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
      />
      <!--<VBtn to="agregar-periodo" class="mx-2" prepend-icon="ri-add-fill"> Agregar</VBtn>-->
      <QuickControl
        toCreate
        type="period"
        @itemCreated="handleItemRefresh"
      />
    </VCardTitle>
    <VDataTable
      :items="items"
      :search="search"
      :headers="headers"
      :sort-by="[{key: 'name'}]"
    >
      <template v-slot:item.is_current="{item}">
        <VChip :color="item.is_current ? 'green' : ''" >
          {{ periodActivityFormatter(item.is_current)}}
        </VChip>
      </template>
      <!--<template v-slot:item.link="{item, index}">-->
      <template v-slot:item.link="{item}">
        <QuickActions
          type="period"
          toEdit
          toDelete
          :index="item.id"
          :name="item.name"
          :toView="item.id + '/grupos-investigacion'"
          :initialData="setInitialData(item)"
          @itemDeleted="handleItemRefresh"
          @itemEdited="handleItemRefresh"
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
import QuickActions from "@/components/quickActions.vue";


interface Item {
  id: number,
  name: string,
  start_date: string,
  end_date: string,
  is_current: boolean,
}

export default defineComponent({
  components:{
    QuickActions
  },
  data() {
    return {
      items: [] as Item[],
      search: '',
      show: false,
      links: '',
      headers: [
        {title: 'ID', key: 'id'},
        {title: 'Nombre', key: 'name'},
        {title: 'Fecha de inicio', key: 'start_date'},
        {title: 'Fecha de finalización', key: 'end_date'},
        {title: 'Estado', key: 'is_current'},
        {key: 'link', sortable: false},
      ],
    }
  },
  // ...
  created() {
    this.getPeriods();
  },
  methods: {

    async getPeriods() {
      const apiHeaders = {
          'API-VERSION': '1',
      }
      try {
        this.items = await API.get(API.ACADEMIC_PERIODS, apiHeaders)
        this.$emit('loaded');
      } catch (error) {
        console.error('Error fetching users:', error);
      }
    },
    periodActivityFormatter(state:boolean){
      return Formatter.periodActivityFormatter(state);
    },
    dateFormatter(date:string){
      return Formatter.dateFormatter(date);
    },
    handleItemRefresh(){
      this.getPeriods();
    },
    setInitialData(item: any) {
      return {
        name: item.name,
        start_date: this.dateFormatter(item.start_date),
        end_date: this.dateFormatter(item.end_date),
        is_current: item.is_current,
      }
    }
  },
})
</script>

