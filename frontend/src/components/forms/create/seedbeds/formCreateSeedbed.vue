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
import API from "@/utils/api";

export default defineComponent({
  name: 'formCreateSeedbed',
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
  data() {
    return {
      loaded: false,
      options: [],
    };
  },
  async created() {
    await this.fetchMapData();
    this.loaded = true;
    this.$emit('loaded');
  },

  methods: {
    // ...existing code...
    handleItemCreated() {
      this.$emit('itemCreated');
    },

    async fetchMapData() {
      try {
        const headers = { 'API-VERSION': '2' }
        // Suppose rawMap is actually an array like:
        // [ { "CIENCIAS_DE_LA_TIERRA_Y_MEDIOAMBIENTALES": "Ciencias...", ... } ]
        const rawMap = await API.get(API.LINES_OF_RESEARCH_BY_INVESTIGATION_VALUES, headers)

        // If rawMap is an array, take the first element. Otherwise, use rawMap directly.
        const actualObject = Array.isArray(rawMap) ? rawMap[0] : rawMap

        // Now convert each key/value into { value, label }
        const transformedOptions = Object.entries(actualObject).map(([key, val]) => ({
          value: key,
          label: val
        }))

        // Locate the field that needs these options
        const linesField = this.fields.find(f => f.key === 'line_of_research')
        if (linesField) {
          linesField.options = linesField.options || []
          linesField.options = [...linesField.options, ...transformedOptions as { label: string; value: string; }[]];
        }
      } catch (error) {
        console.error('Error fetching map data:', error)
      }
    },
  },
});
</script>
```
