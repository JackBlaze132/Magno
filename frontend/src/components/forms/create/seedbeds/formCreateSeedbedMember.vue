<template>
  <!-- Reemplaza tu lógica anterior con formCreateGeneral -->
  <formCreateGeneral
    v-if="loaded"
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
  name: 'formCreateSeedbedProfile',
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
      additionalData: {},
    };
  },
  async created() {
    await this.fetchFunctionaries();
    await this.fetchSeedbeds();
    this.getPeriodId();
    this.getGroupId();
    this.loaded = true;
    this.$emit('loaded');
  },
  methods: {
    handleItemCreated() {
      this.$emit('itemCreated');
    },

    async fetchFunctionaries() {
      const headers = {
        'API-VERSION': '1',
      };
      try {
        const functionaries = await API.get(API.STUDENT_PROFILES_BY_ACADEMIC_PERIOD + this.$route.params.idPeriodo, headers);
        this.$emit('loaded');
        console.log("Hola obtuve los funcionarios")
        console.log(functionaries);

        // Create the options list once
        const functionaryOptions = functionaries.map((functionary: any) => ({
          label: functionary.user.full_name,
          value: functionary.id,
        }));

        // Update all matching fields, not just the first one
        this.fields.forEach(field => {
          if (field.key === 'student_profile_id') {
            field.options = functionaryOptions;
          }
        });
      } catch (error) {
        console.error('Error fetching users:', error);
      }
    },
    async fetchSeedbeds() {
      const headers = {
        'API-VERSION': '1',
      };
      try {
        const seedbeds = await API.get(API.RESEARCH_SEEDBEDS, headers);
        this.$emit('loaded');
        console.log("Hola obtuve los semilleros")
        console.log(seedbeds);

        const groupField = this.fields.find(f => f.key === 'research_seedbed_id')
        if (groupField) {
          groupField.options = seedbeds.map((seedbed: any) => ({
            label: seedbed.name,
            value: seedbed.id,
          }));
        }
      } catch (error) {
        console.error('Error fetching users:', error);
      }
    },

    getPeriodId() {
      try {
        const periodId = this.$route.params.idPeriodo;
        if (periodId) {
          // Agregar el userId al objeto additionalData que se pasará al formulario
          this.additionalData = {
            ...this.additionalData,
            academic_period_id: periodId
          };
          console.log("ID de periodo:", periodId);
        }
      } catch (error) {
        console.error('Error getting user ID from route:', error);
      }

    },

    getGroupId() {
      try {
        const SeedbedId = this.$route.params.idSemillero;
        if (SeedbedId) {
          // Agregar el userId al objeto additionalData que se pasará al formulario
          this.additionalData = {
            ...this.additionalData,
            research_seedbed_profile_id: SeedbedId
          };
          console.log("ID de grupo:", SeedbedId);
        }
      } catch (error) {
        console.error('Error getting user ID from route:', error);
      }

    },
  },


});
</script>
```
