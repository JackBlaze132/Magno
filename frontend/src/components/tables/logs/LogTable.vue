<script lang="ts">
import { defineComponent, PropType } from "vue"
import Formatter from "@/utils/formatter";

export default defineComponent({
  props: {
    items: {
      type: Array as PropType<any[]>,
      required: true
    },
    type: {
      type: String as PropType<'ACTION' | 'CRONJOB' | 'ERROR'>,
      required: true
    },
    loading: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      search: '',
    }
  },
  computed: {
    headers() {
      if (this.type === 'ACTION') {
        return [
          { title: 'ID', key: 'id'},
          { title: 'Método', key: 'httpMethod'},
          { title: 'Endpoint', key: 'requestUrl' },
          { title: 'Estado', key: 'responseStatus' },
          { title: 'Usuario', key: 'userEmail' },
          { title: 'Fecha', key: 'timestamp' },
          { title: 'Tiempo (ms)', key: 'executionTimeMs' }
        ];
      } else if (this.type === 'CRONJOB') {
        return [
          { title: 'ID', key: 'id' },
          { title: 'Nombre del Job', key: 'jobName' },
          { title: 'Estado', key: 'status' },
          { title: 'Inicio', key: 'startTime' },
          { title: 'Duración (ms)', key: 'durationMs'},
          { title: 'Detalles', key: 'details' }
        ];
      } else {
        return [
          { title: 'ID', key: 'id', width: '60' },
          { title: 'Código', key: 'errorCode', width: '150' },
          { title: 'Método', key: 'httpMethod', width: '90' },
          { title: 'Endpoint', key: 'requestUrl' },
          { title: 'Mensaje', key: 'errorMessage' },
          { title: 'Usuario', key: 'userEmail' },
          { title: 'Fecha', key: 'timestamp' }
        ];
      }
    }
  },
  methods: {
    formatDate(date: string) {
      return date ? new Date(date).toLocaleString() : 'N/A';
    },
    getStatusColor(status: number) {
      if (status >= 200 && status < 300) return 'success';
      if (status >= 300 && status < 400) return 'info';
      if (status >= 400 && status < 500) return 'warning';
      return 'error';
    },
    getMethodColor(method: string) {
      const colors: Record<string, string> = {
        'GET': 'primary',
        'POST': 'success',
        'PUT': 'warning',
        'DELETE': 'error',
        'PATCH': 'info'
      };
      return colors[method] || 'default';
    },
    truncateUrl(url: string) {
      if (!url) return 'N/A';
      const apiIndex = url.indexOf('/api/');
      if (apiIndex !== -1) {
        return url.substring(apiIndex);
      }
      return url;
    }
  }
})
</script>

<template>
  <VCard flat>
    <VCardTitle class="d-flex align-center justify-end">
      <VTextField
        v-model="search"
        density="compact"
        label="Buscar logs..."
        prepend-inner-icon="ri-search-line"
        variant="outlined"
        hide-details
        single-line
        class="max-width-300"
      ></VTextField>
    </VCardTitle>

    <VDataTable
      :items="items"
      :search="search"
      :headers="headers"
      :loading="loading"
    >
      <template v-slot:item.timestamp="{ item }">
        {{ formatDate(item.timestamp) }}
      </template>

      <template v-slot:item.startTime="{ item }">
        {{ formatDate(item.startTime) }}
      </template>

      <template v-slot:item.httpMethod="{ item }">
        <VChip
          :color="getMethodColor(item.httpMethod)"
          size="small"
        >
          {{ item.httpMethod }}
        </VChip>
      </template>

      <template v-slot:item.requestUrl="{ item }">
        <span :title="item.requestUrl" class="text-truncate d-block" style="max-width: 300px;">
          {{ truncateUrl(item.requestUrl) }}
        </span>
      </template>

      <template v-slot:item.responseStatus="{ item }">
        <VChip
          :color="getStatusColor(item.responseStatus)"
          size="small"
        >
          {{ item.responseStatus }}
        </VChip>
      </template>

      <template v-slot:item.status="{ item }">
        <VChip
          :color="item.status === 'SUCCESS' ? 'success' : 'error'"
          size="small"
        >
          {{ item.status }}
        </VChip>
      </template>

      <template v-slot:item.executionTimeMs="{ item }">
        <span :class="{'text-error': item.executionTimeMs > 1000, 'text-warning': item.executionTimeMs > 500}">
          {{ item.executionTimeMs }}
        </span>
      </template>

      <template v-slot:item.durationMs="{ item }">
        <span :class="{'text-error': item.durationMs > 10000, 'text-warning': item.durationMs > 5000}">
          {{ item.durationMs }}
        </span>
      </template>
    </VDataTable>
  </VCard>
</template>

