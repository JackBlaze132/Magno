<template>
  <!-- Reemplaza tu lógica anterior con formCreateGeneral -->
  <formCreateGeneral
    :v-if="loaded"
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
  name: 'formCreateUser',
  emits: ['itemCreated', 'loaded'],
  data(){
    return {
      loaded: false,
      options: [],
    }
  },
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
  async created() {
    await this.fetchTypes();
    this.loaded = true;
  },
  methods: {
    // ...existing code...
    handleItemCreated() {
      this.$emit('itemCreated');
    },
    async fetchTypes() {
      const headers = {
        'API-VERSION': '1',
      };
      try {
        const integraTyupes = await API.get(API.INTEGRA_USER_TYPES, headers);
        this.$emit('loaded');
        //console.log(integraTyupes);
        //console.log("Hola obtuve los roles")
        //console.log(this.periods)

        const typesField = this.fields.find(f => f.key === 'type')
        if (typesField) {
          typesField.options = integraTyupes.map((intType: any) => ({
            label: intType,
            value: intType.toUpperCase(),
          }));
        }
      } catch (error) {
        console.error('Error fetching users:', error);
      }
    },
  },
});
</script>
```
