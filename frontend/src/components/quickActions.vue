<template>
  <VBtn v-if="toView" icon class="action view"   flat color="transparent" desity="compact" :to="toView">
    <VIcon icon="ri-eye-line" />
    <VTooltip activator="parent" location="top">
      Ver
    </VTooltip>
  </VBtn>
  <VBtn v-if="toEdit" icon class="action edit" flat color="transparent" desity="compact" :to="toEdit">
    <VIcon icon="ri-edit-box-line" />
    <VTooltip activator="parent" location="top">
      Editar
    </VTooltip>
  </VBtn>
  <VBtn v-if="toDelete" icon class="action delete" flat color="transparent" desity="compact" @click="overlay = !overlay">
    <VIcon icon="ri-delete-bin-5-line" />
    <VOverlay v-model="overlay" scrim="black" class="d-flex align-center justify-center" opacity="0.7">
      <FormDeleteGeneral :index="toDelete" :itemType="deleteType" :itemName="deleteItem" @itemDeleted="handleItemDeleted"/>
    </VOverlay>
    <VTooltip activator="parent" location="top">
      Eliminar
    </VTooltip>

  </VBtn>
</template>


<script lang="ts">
  import { defineComponent } from 'vue';
  import FormDeleteGeneral from './forms/Delete/formDeleteGeneral.vue';

  export default defineComponent({
    name: 'quickActions',
    props: {
      toView:{
        type: String,
      },
      toEdit:{
        type: String,
      },
      toDelete:{
        type: Number,
      },
      deleteType:{
        type: String,
      },
      deleteItem:{
        type: String,
        required: true,
      }
    },
    data(){
      return{
        overlay: false
      }
    },
    methods:{
      handleItemDeleted(index: any){
        this.$emit('itemDeleted', index);
        this.overlay = false;
      }
    }

  })
</script>



