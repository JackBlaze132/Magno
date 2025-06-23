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
    await this.fetchGroups();
    this.getPeriodId();
    this.loaded = true;
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
        const functionaries = await API.get(API.FUNCTIONARY_PROFILES_BY_ACADEMIC_PERIOD + this.$route.params.idPeriodo, headers);
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
          if (field.key === 'coordinator_id' || field.key === 'tutor_id') {
            field.options = functionaryOptions;
          }
        });
      } catch (error) {
        console.error('Error fetching users:', error);
      }
    },
    async fetchGroups() {
      const headers = {
        'API-VERSION': '1',
      };
      try {
        const groups = await API.get(API.RESEARCH_SEEDBEDS, headers);
        this.$emit('loaded');
        console.log("Hola obtuve los semilleros")
        console.log(groups);

        const groupField = this.fields.find(f => f.key === 'research_seedbed_id')
        if (groupField) {
          groupField.options = groups.map((group: any) => ({
            label: group.name,
            value: group.id,
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
          console.log("ID de funcionario obtenido:", periodId);
        }
      } catch (error) {
        console.error('Error getting user ID from route:', error);
      }

    },

    getGroupId() {
      try {
        const groupId = this.$route.params.idGrupo;
        if (groupId) {
          // Agregar el userId al objeto additionalData que se pasará al formulario
          this.additionalData = {
            ...this.additionalData,
            investigation_group_profile_id: groupId
          };
          console.log("ID de funcionario obtenido:", groupId);
        }
      } catch (error) {
        console.error('Error getting user ID from route:', error);
      }

    },
  },


});
</script>
```
