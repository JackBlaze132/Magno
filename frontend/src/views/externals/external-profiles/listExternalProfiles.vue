<template	>
  <VCard flat color="transparent">
    <h1>Perfiles del Aliado Externo</h1>
    <v-breadcrumbs :items="items">
      <template v-slot:divider>
        <v-icon icon="ri-arrow-right-s-line"></v-icon>
      </template>
    </v-breadcrumbs>
    <VOverlay :model-value="loading" class="d-flex align-center justify-center" opacity="0.85" persistent contained>
      <v-progress-circular indeterminate color="primary" size="64"/>
    </VOverlay>
    <TableExternalProfiles @loaded="onChildLoeaded"/>
  </VCard>
</template>

<script lang="ts">
import LoadingManager from '@/utils/loadingManager';

export default{
  data(){
    return{
      loading:true,
      items: [
        { title: 'Aliados Externos', disabled: false, to: '/aliados-externos' },
        { title: 'Perfiles del aliado', disabled: true },
      ]
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
