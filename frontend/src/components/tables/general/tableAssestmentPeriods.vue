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
      ></VTextField>
      <VBtn to="agregar-periodo" class="mx-2" prepend-icon="ri-add-fill"> Agregar</VBtn>
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
          :toView="item.id + '/grupos-investigacion'"

          :toEdit="item.id"
          :itemEdit="item.name"
          @itemEdited="handleItemEdited"
          typeEdit="periodo"

          :toDelete="item.id"
          :itemDelete="item.name"
          @itemDeleted="handleItemDeleted"
          typeDelete="periodo"

          :fields="[
          { key: 'name', label: 'Nombre', type: 'text' },
          { key: 'start_date', label: 'Fecha de inicio', type: 'date' },
          { key: 'end_date', label: 'Fecha de fin', type: 'date' }
          ]"

          :initialData="{
            name: item.name,
            start_date: dateFormatter(item.start_date),
            end_date: dateFormatter (item.end_date),
            is_current: item.current
          }"

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
import { VChip } from "vuetify/components";
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
        this.items = await API.get(API.GET_ACADEMIC_PERIODS, apiHeaders)
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

    handleItemDeleted(index: number) {
      this.items.splice(index, 1);
      this.getPeriods() // Eliminar el elemento del array
    },
    handleItemEdited(index: number, updatedName: string) {
      const item = this.items.find((i) => i.id === index);
      if (item) {
        item.name = updatedName;
      }
      this.getPeriods();
    },
  },
})
</script>

