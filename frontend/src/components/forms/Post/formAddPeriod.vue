```vue
// filepath: c:\Users\ederd\Documents\Github\Unibague\Magno\frontend\src\components\forms\Post\formAddPeriod.vue
<template>
  <!-- Reemplaza tu lógica anterior con formCreateGeneral -->
  <formCreateGeneral
    :type="type"
    :fields="fields"
    :name="name"
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
  methods: {
    // ...existing code...
    handleItemCreated() {
      this.$emit('itemCreated');
    },
  },
});
</script>
```
