<template>
  <VCard variant="flat" color="transparent">
    <VOverlay :model-value="loading" class="d-flex align-center justify-center" opacity="0.85" persistent contained>
      <v-progress-circular indeterminate color="primary" size="64"/>
    </VOverlay>
    <h1>Grupos de investigación</h1>
    <v-breadcrumbs :items="breadcrumbItems">
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
import API from "@/utils/api";

export default{
  components: {TableGroupsProfiles},
  data(){
    return{
      loading:true,
      periodName: ''
    }
  },
  computed: {
    breadcrumbItems() {
      return [
        { title: 'Inicio', disabled: false, to: '/inicio' },
        { title: this.periodName, disabled: false, to: '/periodos' },
        { title: 'Grupos de investigación', disabled: true }
      ];
    }
  },
  async created(){
    LoadingManager.setTotalComponents(1);
    LoadingManager.reset();
    await this.fetchPeriodName();
  },
  methods:{
    async fetchPeriodName() {
      const idPeriodo = this.$route.params.idPeriodo;
      if (idPeriodo) {
        try {
          const headers = { 'API-VERSION': '1' };
          const data = await API.get(API.ACADEMIC_PERIODS + idPeriodo, headers);
          // API returns an array, take the first element
          const period = Array.isArray(data) ? data[0] : data;
          if (period && period.name) {
            this.periodName = period.name;
          }
        } catch (error) {
          console.error('Error fetching period name:', error);
        }
      }
    },
    onChildLoeaded (){
      LoadingManager.onChildLoaded();
      if (LoadingManager. allComponentsLoaded()){
        this.loading = false;
      }
    }
  }
}
</script>
