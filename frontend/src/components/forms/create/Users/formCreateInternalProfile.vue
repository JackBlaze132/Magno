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
    }
  },
  data() {
    return {
      loaded: false,
      additionalData: {},
    };
  },
  async created() {
    await this.fetchPeriods();
    await this.fetchRoles();
    this.getUserId();
    this.loaded = true;
    this.$emit('loaded');
  },
  methods: {
    handleItemCreated() {
      this.$emit('itemCreated');
    },
    async fetchPeriods() {
      const headers = {
        'API-VERSION': '1',
      };
      try {
        const periods = await API.get(API.VISIBLE_ACADEMIC_PERIODS, headers);
        this.$emit('loaded');
        console.log("Hola obtuve los periodos")

        const periodField = this.fields.find(f => f.key === 'academic_period_id')
        if (periodField) {
          periodField.options = periods.map((period: any) => ({
            label: period.name,
            value: period.id,
          }));
        }
      } catch (error) {
        console.error('Error fetching users:', error);
        }
    },

    async fetchRoles() {
      const headers = {
        'API-VERSION': '1',
      };
      try {
        const roles = await API.get(API.ROLES, headers);
        this.$emit('loaded');
        console.log("Hola obtuve los roles")
        console.log(roles)

        const rolesField = this.fields.find(f => f.key === 'role_id')
        if (rolesField) {
          rolesField.options = roles.map((role: any) => ({
            label: role.name,
            value: role.id,
          }));
        }
      } catch (error) {
        console.error('Error fetching users:', error);
        }
    },
    getUserId() {
      try {
        const userId = this.$route.params.idFunctionary || this.$route.params.idStudent;
        if (userId) {
          // Agregar el userId al objeto additionalData que se pasará al formulario
          this.additionalData = {
            ...this.additionalData,
            user_id: userId
          };
          console.log("ID de funcionario obtenido:", userId);
        }
      } catch (error) {
        console.error('Error getting user ID from route:', error);
      }
    },
  },
});
</script>
```
