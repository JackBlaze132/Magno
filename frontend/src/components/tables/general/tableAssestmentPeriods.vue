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
      <template v-slot:item.isActive="{item}">
        <VChip :color="item.isActive ? 'green' : ''" >
          {{ periodActivityFormatter(item.isActive)}}
        </VChip>
      </template>
      <!--<template v-slot:item.link="{item, index}">-->
        <template v-slot:item.link="{item}">
        <QuickActions
          :toView="item.id + '/grupos-investigacion'"
          :toEdit="item.id + '/editar-periodo'"
          :toDelete="item.id"
          :deleteItem="item.name"
          @itemDeleted="handleItemDeleted"
          deleteType="periodo"
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
import { VChip } from "vuetify/components";
import QuickActions from "@/components/quickActions.vue";


interface Item {
  id: number,
  name: string,
  startDate: string,
  endDate: string,
  isActive: boolean,
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
        {title: 'Fecha de inicio', key: 'startDate'},
        {title: 'Fecha de finalización', key: 'endDate'},
        {title: 'Estado', key: 'isActive'},
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
      try {
        this.items = await API.get(API.GET_ASSESMENT_PERIODS)
        this.$emit('loaded');
      } catch (error) {
        console.error('Error fetching users:', error);
      }
    },
    periodActivityFormatter(state:boolean){
      return Formatter.periodActivityFormatter(state);
    },
    handleItemDeleted(index: number) {
      this.items.splice(index, 1);
      this.getPeriods() // Eliminar el elemento del array
    },
  },
})
</script>

