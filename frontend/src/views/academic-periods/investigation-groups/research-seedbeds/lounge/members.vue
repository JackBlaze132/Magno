<script setup lang="ts">


import { VCard } from 'vuetify/components';
import API from "@/utils/api";
import LoadingManager from '@/utils/loadingManager';

</script>

<script lang="ts">
interface Item{
  research_seedbed: {
    name: string;
  },
}

export default {
  data(){
    return {
      item: {} as Item,
      loading: true,
      periodName: '',
      groupName: '',
      seedbedName: '',
    }
  },
  computed: {
    breadcrumbItems() {
      return [
        { title: 'Inicio', disabled: false, to: '/inicio' },
        { title: this.periodName || 'Períodos Académicos', disabled: false, to: '/periodos' },
        { title: this.groupName || 'Grupos de Investigación', disabled: false, to: '../../../' },
        { title: this.seedbedName || 'Semilleros de Investigación', disabled: false, to: '../' },
        { title: 'Miembros', disabled: true },
      ];
    }
  },
  created() {
    LoadingManager.setTotalComponents(4);
    LoadingManager.reset();
    this.getData();
  },
  methods: {
    async getData(){
      const headers = { 'API-VERSION': '1' };
      try {
        const idSemillero = this.$route.params.idSemillero;
        const idGrupo = this.$route.params.idGrupo;
        const idPeriodo = this.$route.params.idPeriodo;

        const [seedbedData, groupData, periodData] = await Promise.all([
          API.get(API.RESEARCH_SEEDBEDS_PROFILES + idSemillero, headers),
          API.get(API.INVESTIGATION_GRUOPS_PROFILES + idGrupo, headers),
          API.get(API.VISIBLE_ACADEMIC_PERIODS + idPeriodo, headers)
        ]);

        const seedbedProfile = Array.isArray(seedbedData) ? seedbedData[0] : seedbedData;
        if (seedbedProfile && seedbedProfile.research_seedbed) {
          this.item = seedbedProfile;
          this.seedbedName = seedbedProfile.research_seedbed.name;
        }

        const groupProfile = Array.isArray(groupData) ? groupData[0] : groupData;
        if (groupProfile && groupProfile.investigation_group) {
          this.groupName = groupProfile.investigation_group.name;
        }

        const period = Array.isArray(periodData) ? periodData[0] : periodData;
        if (period) {
          this.periodName = period.name;
        }

      } catch (error) {
        console.error('Error fetching breadcrumb data:', error);
      }
    },
    onChildLoeaded (){
      LoadingManager.onChildLoaded();
      if (LoadingManager. allComponentsLoaded()){
        this.loading = false;
      }

    }
  },
}
</script>

<template>
  <h1 v-if="item && item.research_seedbed">{{ item.research_seedbed.name }}</h1>
  <v-breadcrumbs :items="breadcrumbItems" density="compact" class="resposive-breadcrumbs">
      <template v-slot:divider>
        <v-icon icon="ri-arrow-right-s-line"></v-icon>
      </template>
    </v-breadcrumbs>

  <VCard class="pa-5 my-3" rounded="lg">
    <VOverlay :model-value="loading" class="d-flex align-center justify-center" opacity="0.85" persistent contained>
      <v-progress-circular indeterminate color="primary" size="64"/>
    </VOverlay>
    <tableCoordinator @loaded="onChildLoeaded"/>
    <tableTeachers @loaded="onChildLoeaded"/>
    <tableMembers @loaded="onChildLoeaded"/>
    <TableExternal @loaded="onChildLoeaded"/>


  </VCard>
</template>

