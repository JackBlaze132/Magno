<template>
  <formUpdateGeneral
    v-if="loaded"
    :type="type"
    :fields="fields"
    :index="index"
    :initialData="transformedInitialData"
    @itemEdited="handleItemEdited"
  />
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import API from "@/utils/api";

export default defineComponent({
  name: 'formUpdateSeedbed',
  emits: ['itemEdited'],
  data() {
    return {
      loaded: false,
      valueToLabelMap: {} as Record<string, string>,
      labelToValueMap: {} as Record<string, string>,
      transformedInitialData: {} as Record<string, any>,
    };
  },
  props: {
    fields: {
      type: Array as () => Array<{ key: string; label: string; type?: string; options?: Array<{ label: string; value: string }> }>,
      default: () => [],
    },
    type: {
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
  async created() {
    await this.fetchMapData()
    this.transformInitialData()
    this.loaded = true
  },
  methods: {
    handleItemEdited() {
      this.$emit('itemEdited');
    },

    transformInitialData() {
      // Create a copy of initialData
      this.transformedInitialData = { ...this.initialData }

      // Transform line_of_research from label to value if needed
      if (this.transformedInitialData.line_of_research) {
        const currentValue = this.transformedInitialData.line_of_research
        const currentValueLower = String(currentValue).toLowerCase()

        // Check if the current value is a label (not an enum value)
        // If it exists in labelToValueMap, convert it (case-insensitive lookup)
        if (this.labelToValueMap[currentValueLower]) {
          this.transformedInitialData.line_of_research = this.labelToValueMap[currentValueLower]
        }
      }
    },

    async fetchMapData() {
      try {
        const headers = { 'API-VERSION': '2' }
        // Get the lines of research mapping from the API
        const rawMap = await API.get(API.LINES_OF_RESEARCH_BY_INVESTIGATION_VALUES, headers)

        // If rawMap is an array, take the first element. Otherwise, use rawMap directly.
        const actualObject = Array.isArray(rawMap) ? rawMap[0] : rawMap

        // Create both mappings: value->label and label->value
        Object.entries(actualObject).forEach(([key, val]) => {
          this.valueToLabelMap[key] = String(val)
          this.labelToValueMap[String(val).toLowerCase()] = key
        })

        // Convert to options format for the select field
        const transformedOptions = Object.entries(actualObject).map(([key, val]) => ({
          value: key,
          label: String(val)
        }))

        // Locate the field that needs these options
        const linesField = this.fields.find(f => f.key === 'line_of_research')
        if (linesField) {
          linesField.options = transformedOptions
        }
      } catch (error) {
        console.error('Error fetching map data:', error)
      }
    },
  },
});
</script>
