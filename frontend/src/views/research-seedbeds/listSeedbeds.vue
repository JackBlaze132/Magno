<script lang="ts">
import TableSeedbeds from '@/components/tables/seedbeds/tableSeedbeds.vue';
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
<template>
  <VCard variant="flat" color="transparent">
    <VOverlay :model-value="loading" contained class="d-flex align-center justify-center" opacity="0.85" persistent>
      <v-progress-circular indeterminate color="primary" size="64"/>
    </VOverlay>
    <TableSeedbeds @loaded="onChildLoeaded"/>
  </VCard>
</template>
