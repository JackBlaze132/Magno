<template>
  <VBtn
    :loading="loading"
    rounded="xl"
    class="my-2 text-capitalize"
    :prepend-icon="$props.icon"
    :color="color"
    type="submit"
    :text="$props.text">
  </VBtn>
</template>

<script lang="ts">
  //import { cloneNode } from '@babel/types';
  import { defineComponent } from 'vue';

  export default defineComponent({
    name: 'loadingBtn',
    props: {
      loading:{
        type:Boolean,
      },
      icon: {
        type: String,
      },
      text:{
        type:String,
      },
      color:{
        type:String,
        default: 'black'
      }
    }
  })
</script>
