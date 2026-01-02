<template>
  <!-- Reemplaza tu lógica anterior con formCreateGeneral -->
  <formCreateGeneral
    v-if="loaded"
    :index="index"
    :type="type"
    :fields="fields"
    :name="name"
    :additionalData="additionalData"
    @itemCreated="handleItemCreated"
  />
</template>

<script lang="ts">
// ...existing code...
import { defineComponent } from 'vue';
import API from '@/utils/api';
export default defineComponent({
  name: 'formCreateUser',
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
    },
    index: {
      type: [String, Number],
      default: null,
      required: false,
    }
  },
  data() {
    return {
      loaded: false,
      additionalData: {},
    };
  },
  async created() {
    await this.fetchTypes();
    await this.fetchSex();
    this.setAffiliation();
    this.loaded = true;
  },

  methods: {
    handleItemCreated() {
      this.$emit('itemCreated');
    },

    async fetchTypes() {
      const headers = {
        'API-VERSION': '1',
      };
      try {
        const integraTypes = await API.get(API.USERS_DIRI, headers);
        this.$emit('loaded');
        //console.log(integraTyupes);
        //console.log("Hola obtuve los roles")
        //console.log(this.periods)

        const typesField = this.fields.find(f => f.key === 'type')
        if (typesField) {
          typesField.options = integraTypes.map((intType: any) => ({
            label: intType,
            value: intType.toUpperCase(),
          }));
        }
      } catch (error) {
        console.error('Error fetching users:', error);
      }
    },

    async fetchSex() {
      const headers = {
        'API-VERSION': '1',
      };
      try {
        const sexValues = await API.get(API.SEX_VALUES, headers);

        //console.log(integraTyupes);
        //console.log("Hola obtuve los roles")
        //console.log(this.periods)

        const sexField = this.fields.find(f => f.key === 'sex')
        if (sexField) {
          sexField.options = sexValues.map((intType: any) => ({
            label: intType,
            value: intType.toUpperCase(),
          }));
        }
      } catch (error) {
        console.error('Error fetching users:', error);
      }
    },

    setAffiliation() {
      try {
        // Agregar el userId al objeto additionalData que se pasará al formulario
        this.additionalData = {
          ...this.additionalData,
          is_external_user: true
        };
        console.log("ID del periodo :", this.additionalData);
      } catch (error) {
        console.error('Error getting user ID from route:', error);
      }
    },
  }
});
</script>
```
