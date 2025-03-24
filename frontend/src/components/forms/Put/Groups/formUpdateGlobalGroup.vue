<template>
  <formUpdateGeneral
    v-if="loaded"
    :type="type"
    :fields="fields"
    :index="index"
    :initialData="initialData"
    @itemUpdated="handleItemUpdated"
  />
</template>

<script lang="ts">
// ...existing code...
import { defineComponent } from 'vue';
import API from "@/utils/api";

export default defineComponent({
  name: 'formUpdateGlobalGroup',
  emits: ['itemEdited'],
  data() {
    return {
      loaded: false,
      options: [],
    };
  },
  async created() {
    await this.fetchMapData()
    this.loaded = true
  },
  props:{
    fields:{
      type: Array as () => Array<{ key: string; label: string; type?: string; options?: Array<{ label: string; value: string }> }>,
      default: () => [],
    },
    type:{
      type: String,
    },
    index: {
      type: Number,
    },
    initialData: {
      type: Object,
      default: () => ({}),
    },
  },
  methods: {
    // ...existing code...
    handleItemUpdated() {
      this.$emit('itemEdited');
    },

    async fetchMapData() {
      try {
        const headers = { 'API-VERSION': '2' }
        // Suppose rawMap is actually an array like:
        // [ { "CIENCIAS_DE_LA_TIERRA_Y_MEDIOAMBIENTALES": "Ciencias...", ... } ]
        const rawMap = await API.get('enums/LineOfResearch/values', headers)

        // If rawMap is an array, take the first element. Otherwise, use rawMap directly.
        const actualObject = Array.isArray(rawMap) ? rawMap[0] : rawMap

        // Now convert each key/value into { value, label }
        const transformedOptions = Object.entries(actualObject).map(([key, val]) => ({
          value: key,
          label: val
        }))

        // Locate the field that needs these options
        const linesField = this.fields.find(f => f.key === 'lines_of_research')
        if (linesField) {
          linesField.options = linesField.options || []
          linesField.options = [...linesField.options, ...transformedOptions]
          console.log('Updated lines_of_research options:', linesField.options)
        }
      } catch (error) {
        console.error('Error fetching map data:', error)
      }
    },
  },
});
</script>
