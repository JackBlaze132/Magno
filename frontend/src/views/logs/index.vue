<script lang="ts">
import { defineComponent } from "vue"
import LogService, { LogType, LogFilters } from "@/utils/logService";
import LogTable from "@/components/tables/logs/LogTable.vue";

export default defineComponent({
  components: {
    LogTable
  },
  data() {
    return {
      selectedType: 'ACTION' as LogType,
      logTypes: [
        { title: 'Logs de Acciones', value: 'ACTION' },
        { title: 'Logs de Cronjobs', value: 'CRONJOB' },
        { title: 'Logs de Errores', value: 'ERROR' }
      ],
      statusOptions: [
        { title: 'Éxito', value: 'SUCCESS' },
        { title: 'Error', value: 'FAILURE' }
      ],
      logs: [],
      loading: false,
      filters: {
        userId: '',
        startDate: null as Date | null,
        endDate: null as Date | null,
        jobName: '',
        status: ''
      }
    }
  },
  watch: {
    selectedType() {
      this.fetchLogs();
    }
  },
  created() {
    this.fetchLogs();
  },
  methods: {
    async fetchLogs() {
      this.loading = true;
      try {
        // Only pass filters that have values
        const activeFilters: LogFilters = {};
        if (this.filters.userId) activeFilters.userId = this.filters.userId;
        if (this.filters.startDate) activeFilters.startDate = this.filters.startDate.toISOString().replace('Z', '');
        if (this.filters.endDate) activeFilters.endDate = this.filters.endDate.toISOString().replace('Z', '');
        if (this.filters.jobName) activeFilters.jobName = this.filters.jobName;
        if (this.filters.status) activeFilters.status = this.filters.status;

        this.logs = await LogService.fetchLogs(this.selectedType, activeFilters);
      } catch (error) {
        console.error("Error fetching logs:", error);
      } finally {
        this.loading = false;
      }
    },
    clearFilters() {
      this.filters = {
        userId: '',
        startDate: null,
        endDate: null,
        jobName: '',
        status: ''
      };
      this.fetchLogs();
    }
  }
})
</script>

<template>
    <VRow>
      <VCol cols="12">
        <h1>Gestión de Logs</h1>
      </VCol>
    </VRow>

    <VRow>
      <VCol cols="12" md="3">
        <VSelect
          v-model="selectedType"
          :items="logTypes"
          label="Tipo de Log"
          variant="outlined"
          density="compact"
        />
      </VCol>

      <VCol cols="12" md="9">
        <VRow density="compact" align="center">
          <!-- Action/Error Specific Filters -->
          <template v-if="selectedType !== 'CRONJOB'">
            <VCol cols="12" sm="4" md="3">
              <VTextField
                v-model="filters.userId"
                label="User ID"
                variant="outlined"
                density="compact"
                hide-details
              />
            </VCol>
          </template>

          <!-- Cronjob Specific Filters -->
          <template v-if="selectedType === 'CRONJOB'">
            <VCol cols="12" sm="4" md="2">
              <VTextField
                v-model="filters.jobName"
                label="Nombre del Job"
                variant="outlined"
                density="compact"
                hide-details
              />
            </VCol>
            <VCol cols="12" sm="4" md="2">
              <VSelect
                v-model="filters.status"
                :items="statusOptions"
                label="Estado"
                variant="outlined"
                density="compact"
                hide-details
                clearable
              />
            </VCol>
          </template>

          <!-- Common Date Filter -->
          <VCol cols="6" sm="4" md="2">
            <VDateInput
              v-model="filters.startDate"
              label="Desde"
              density="compact"
              prepend-inner-icon="ri-calendar-start"
              prepend-icon=""
              hide-details
            />
          </VCol>

          <VCol cols="6" sm="4" md="2">
            <VDateInput
              v-model="filters.endDate"
              label="Hasta"
              density="compact"
              prepend-inner-icon="ri-calendar-start"
              prepend-icon=""
              hide-details
            />
          </VCol>

          <VCol cols="12" md="3" class="d-flex align-center gap-2">
            <VBtn
              color="primary"
              prepend-icon="ri-filter-line"
              @click="fetchLogs"
              :loading="loading"
            >
              Filtrar
            </VBtn>

            <VBtn
              variant="text"
              @click="clearFilters"
            >
              Limpiar
            </VBtn>
          </VCol>
        </VRow>
      </VCol>
    </VRow>

    <VRow>
      <VCol cols="12">
        <LogTable
          :items="logs"
          :type="selectedType"
          :loading="loading"
          @refresh="fetchLogs"
        />
      </VCol>
    </VRow>
</template>

<style scoped>
.gap-2 {
  gap: 8px;
}
.max-width-150 {
  max-width: 150px;
}
</style>
