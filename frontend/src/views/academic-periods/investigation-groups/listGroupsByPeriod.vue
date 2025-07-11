<script setup lang="ts">
const items = [
  { title: 'Inicio', disabled: false, to: '/inicio' },
  { title: 'Períodos Académicos', disabled: false, to: '/periodos' },
  { title: 'Grupos de Investigación', disabled: true }
];
</script>

<template>
  <VCard variant="flat" color="transparent">
    <VOverlay :model-value="loading" class="d-flex align-center justify-center" opacity="0.85" persistent contained>
      <v-progress-circular indeterminate color="primary" size="64"/>
    </VOverlay>
    <h1>Grupos de investigación</h1>
    <v-breadcrumbs :items="items">
      <template v-slot:divider>
        <v-icon icon="ri-arrow-right-s-line"></v-icon>
      </template>
    </v-breadcrumbs>
    <table-groups-profiles @loaded="onChildLoeaded"/>
  </VCard>
</template>

<script lang="ts">
import LoadingManager from '@/utils/loadingManager';
import TableGroupsProfiles from "@/components/tables/groups/tableGroupsProfiles.vue";
export default{
  components: {TableGroupsProfiles},
  data(){
    return{
      loading:true
    }
  },
  created(){
    LoadingManager.setTotalComponents(1);
    LoadingManager.reset();
  },
  methods:{
    onChildLoeaded (){
      LoadingManager.onChildLoaded();
      if (LoadingManager. allComponentsLoaded()){
        this.loading = false;
      }
    }
  }

}

</script>
