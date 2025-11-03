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
      items: [
        { title: 'Inicio', disabled: false, to: '/inicio' },
        { title: 'Períodos Académicos', disabled: false, to: '/periodos' },
        { title: 'Grupos de Investigación', disabled: false, to: '../../../' },
        { title: 'Semilleros de Investigación', disabled: false, to: '../' },
      ],
    }
  },
  // ...
  created() {
    //this.getData();
    LoadingManager.setTotalComponents(4);
    LoadingManager.reset();
    this.getData();
  },
  methods: {
    async getData(){
      const headers = {
        'API-VERSION': '1',
      };
      try {
        const data = await API.get(API.RESEARCH_SEEDBEDS_PROFILES + this.$route.params.idSemillero, headers);
        console.log("Raw API response:", data);
        this.item = data[0];  // API returns an array, take the first element

        if (this.item && this.item.research_seedbed) {
          this.items.push({
            title: this.item.research_seedbed.name,
            disabled: true,
            to: ''
          });
        }

      } catch (error) {
        console.error('Error fetching users:', error);
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
  <v-breadcrumbs :items="items" density="compact" class="resposive-breadcrumbs">
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

