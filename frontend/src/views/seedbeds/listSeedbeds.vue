<template>
  <VCard flat ref="overlayContainer" class="overlay-container" color="transparent">
    <h1>Semilleros</h1>
    <VOverlay :model-value="loading" :attach="$refs.overlayContainer" class="d-flex align-center justify-center" opacity="0.85" persistent contained>
      <v-progress-circular indeterminate color="primary" size="64"/>
    </VOverlay>
    <tableSeedbeds @loaded="onChildLoeaded"/>
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
  computed: {
    overlayContainer() {
      return this.$refs.overlayContainer;
    },
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
