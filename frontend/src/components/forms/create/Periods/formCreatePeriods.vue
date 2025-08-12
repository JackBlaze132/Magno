<template>
  <!-- Reemplaza tu lógica anterior con formCreateGeneral -->
  <formCreateGeneral
    :type="type"
    :fields="fields"
    :label="label"
    @itemCreated="handleItemCreated"
  />
</template>

<script lang="ts">
// ...existing code...
import { defineComponent } from 'vue';

export default defineComponent({
  name: 'formAddPeriod',
  props:{
    name: {
      type: String,
    },
    fields:{
      type: Array as () => Array<{ key: string; label: string; type?: string; options?: Array<{ label: string; value: string }> }>,
      default: () => [],
    },
    type:{
      type: String,
    }
  },
  async created(){
    this.$emit('loaded');
  },

  methods: {
    // ...existing code...
    handleItemCreated() {
      this.$emit('itemCreated');
    },
  },
});
</script>

