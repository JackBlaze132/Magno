<template>
  <h1>Semilleros de investigación</h1>
  <v-breadcrumbs :items="breadcrumbItems">
    <template v-slot:divider>
      <v-icon icon="ri-arrow-right-s-line"></v-icon>
    </template>
  </v-breadcrumbs>
  <VCard flat color="transparent">
    <VOverlay :model-value="loading" class="d-flex align-center justify-center" opacity="0.85" persistent contained>
      <v-progress-circular indeterminate color="primary" size="64"/>
    </VOverlay>
    <tableSeedbedsProfiles @loaded="onChildLoeaded"/>
  </VCard>
</template>

<script lang="ts">
import LoadingManager from '@/utils/loadingManager';
import API from "@/utils/api";

export default{
  data(){
    return{
      loading:true,
      periodName: '',
      groupName: ''
    }
  },
  computed: {
    breadcrumbItems() {
      return [
        { title: 'Inicio', disabled: false, to: '/inicio' },
        { title: this.periodName , disabled: false, to: '/periodos' },
        { title: this.groupName , disabled: false, to: `../../../grupos-investigacion` },
        { title: 'Semilleros de investigación', disabled: true },
      ];
    }
  },
  async created(){
    LoadingManager.setTotalComponents(1);
    LoadingManager.reset();
    await Promise.all([
      this.fetchPeriodName(),
      this.fetchGroupName()
    ]);
  },
  methods:{
    async fetchPeriodName() {
      const idPeriodo = this.$route.params.idPeriodo;
      if (idPeriodo) {
        try {
          const headers = { 'API-VERSION': '1' };
          const data = await API.get(API.ACADEMIC_PERIODS + idPeriodo, headers);
          const period = Array.isArray(data) ? data[0] : data;
          if (period && period.name) {
            this.periodName = period.name;
          }
        } catch (error) {
          console.error('Error fetching period name:', error);
        }
      }
    },
    async fetchGroupName() {
      const idGrupo = this.$route.params.idGrupo;
      if (idGrupo) {
        try {
          const headers = { 'API-VERSION': '1' };
          const data = await API.get(API.INVESTIGATION_GRUOPS_PROFILES + idGrupo, headers);
          const groupProfile = Array.isArray(data) ? data[0] : data;
          if (groupProfile && groupProfile.investigation_group && groupProfile.investigation_group.name) {
            this.groupName = groupProfile.investigation_group.name;
          }
        } catch (error) {
          console.error('Error fetching group name:', error);
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
