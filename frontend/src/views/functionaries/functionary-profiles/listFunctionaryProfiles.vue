<script setup lang="ts">
const items = [
  { title: 'Funcionarios', disabled: false, to: '/funcionarios' },
  { title: 'Perfiles del funcionario', disabled: true },
];
</script>

<template	>
  <VCard flat color="transparent">
    <h1>Perfiles del Funcionario</h1>
    <v-breadcrumbs :items="items">
      <template v-slot:divider>
        <v-icon icon="ri-arrow-right-s-line"></v-icon>
      </template>
    </v-breadcrumbs>
    <VOverlay :model-value="loading" class="d-flex align-center justify-center" opacity="0.85" persistent contained>
      <v-progress-circular indeterminate color="primary" size="64"/>
    </VOverlay>
    <TableFunctionaryProfiles @loaded="onChildLoeaded"/>
  </VCard>
</template>

<script lang="ts">
import LoadingManager from '@/utils/loadingManager';
export default{
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
