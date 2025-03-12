<template>
  <h1>Semilleros</h1>
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
      <VBtn to="agregar-semillero" class="mx-2" prepend-icon="ri-add-fill"> Agregar</VBtn>
    </VCardTitle>
    <VDataTable
      :items="items"
      :search="search"
      :headers="headers"
    >
      <template v-slot:item.active="{item}">
        <VChip :color="item.active ? 'green' : ''" >
          {{ periodActivityFormatter(item.active)}}
        </VChip>
      </template>

      <template v-slot:item.link="{item}">
        <QuickActions
          :toView="item.id.toString()"
          :toEdit="item.id + '/editar-semillero'"
          :toDelete="item.id"
          :deleteItem="item.name"
          deleteType="semillero"
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
import Formatter from "@/utils/formatter";
import quickActions from "@/components/quickActions.vue";


interface Item {
  id: number,
  name: string,
  active: boolean,
  coordninator: {
    name: string
  }
}

export default defineComponent({
  components:{
    quickActions,
  },
  data() {
    return {
      items: [] as Item[],
      search: '',
      links: '',
      headers: [
        {title: 'ID', key: 'id'},
        {title: 'Nombre', key: 'name'},
        {title: 'Coordinador', key: 'coordinator.name'},
        {title: 'Estado', key: 'active'},
        { key: 'link', sortable: false},
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
        this.items = await API.get(API.GET_RESEARCH_SEEDBED_BY_GROUP_ID + this.$route.params.idGrupo);
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
      this.getSeedBeds(); // Eliminar el elemento del array
    },
  },
})
</script>
