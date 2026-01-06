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
      detailDialog: false,
      selectedLog: null as any
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
          { title: 'Tiempo (ms)', key: 'executionTimeMs' },
          { key: 'actions', sortable: false }
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
          { title: 'Fecha', key: 'timestamp' },
          { key: 'actions', sortable: false }
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
    },
    viewDetails(item: any) {
      this.selectedLog = item;
      this.detailDialog = true;
    },
    closeDialog() {
      this.detailDialog = false;
      this.selectedLog = null;
    },
    formatJson(jsonString: string | null) {
      if (!jsonString) return null;
      try {
        return JSON.stringify(JSON.parse(jsonString), null, 2);
      } catch {
        return jsonString;
      }
    },
    formatRequestParams(paramsString: string | null) {
      if (!paramsString) return null;
      try {
        // Remove outer braces if present
        const cleaned = paramsString.replace(/^\{|\}$/g, '');
        // Split by commas and format nicely
        const params = cleaned.split(', ').map(param => {
          const [key, value] = param.split('=');
          return `${key}: ${value}`;
        }).join('\n');
        return params;
      } catch {
        return paramsString;
      }
    }
  }
})
</script>

<template>
  <VCard flat class="pa-5">
    <VCardTitle class="d-flex align-center justify-end">
      <VTextField
        v-model="search"
        density="compact"
        label="Buscar logs..."
        prepend-inner-icon="ri-search-line"
        variant="outlined"
        hide-details
        single-line
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

      <template v-slot:item.actions="{ item }">
        <VBtn
          v-if="type === 'ACTION' || type === 'ERROR'"
          icon="ri-eye-line"
          variant="text"
          size="small"
          @click="viewDetails(item)"
        />
      </template>
    </VDataTable>

    <!-- Detail Dialog for ACTION Logs -->
    <VDialog v-model="detailDialog" max-width="900" scrollable>
      <VCard v-if="selectedLog && type === 'ACTION'">
        <VCardTitle class="d-flex align-center justify-space-between">
          <span>Detalles del Log de Acción #{{ selectedLog.id }}</span>
          <VBtn icon="ri-close-line" variant="text" @click="closeDialog" />
        </VCardTitle>

        <VDivider />

        <VCardText class="pt-4">
          <VRow>
            <!-- Basic Info -->
            <VCol cols="12">
              <h3 class="text-subtitle-1 mb-2">Información General</h3>
            </VCol>

            <VCol cols="6" md="3">
              <div class="text-caption text-medium-emphasis">Método</div>
              <VChip :color="getMethodColor(selectedLog.httpMethod)" size="small" class="mt-1">
                {{ selectedLog.httpMethod }}
              </VChip>
            </VCol>

            <VCol cols="6" md="3">
              <div class="text-caption text-medium-emphasis">Estado</div>
              <VChip :color="getStatusColor(selectedLog.responseStatus)" size="small" class="mt-1">
                {{ selectedLog.responseStatus }}
              </VChip>
            </VCol>

            <VCol cols="6" md="3">
              <div class="text-caption text-medium-emphasis">Tiempo Ejecución</div>
              <div class="text-body-2">{{ selectedLog.executionTimeMs }} ms</div>
            </VCol>

            <VCol cols="6" md="3">
              <div class="text-caption text-medium-emphasis">Fecha</div>
              <div class="text-body-2">{{ formatDate(selectedLog.timestamp) }}</div>
            </VCol>

            <VCol cols="12">
              <div class="text-caption text-medium-emphasis">URL</div>
              <div class="text-body-2 text-break">{{ selectedLog.requestUrl }}</div>
            </VCol>

            <VCol cols="12" md="6">
              <div class="text-caption text-medium-emphasis">Usuario</div>
              <div class="text-body-2">{{ selectedLog.userEmail }}</div>
            </VCol>

            <VCol cols="12" md="6">
              <div class="text-caption text-medium-emphasis">IP Cliente</div>
              <div class="text-body-2">{{ selectedLog.clientIp }}</div>
            </VCol>

            <VCol cols="12">
              <div class="text-caption text-medium-emphasis">User Agent</div>
              <div class="text-body-2 text-break">{{ selectedLog.userAgent }}</div>
            </VCol>

            <!-- Request Body -->
            <VCol cols="12" v-if="selectedLog.requestBody">
              <VDivider class="my-4" />
              <h3 class="text-subtitle-1 mb-2">Request Body</h3>
              <VCard variant="flat" class="bg-background">
                <VCardText>
                  <pre class="json-pre">{{ formatJson(selectedLog.requestBody) }}</pre>
                </VCardText>
              </VCard>
            </VCol>

            <!-- Response Body -->
            <VCol cols="12" v-if="selectedLog.responseBody">
              <VDivider class="my-4" />
              <h3 class="text-subtitle-1 mb-2">Response Body</h3>
              <VCard variant="flat" class="bg-background">
                <VCardText>
                  <pre class="json-pre">{{ formatJson(selectedLog.responseBody) }}</pre>
                </VCardText>
              </VCard>
            </VCol>

            <!-- No Body Messages -->
            <VCol cols="12" v-if="!selectedLog.requestBody && !selectedLog.responseBody">
              <VAlert type="info" variant="tonal" class="mt-4">
                Este log no tiene request body ni response body.
              </VAlert>
            </VCol>
          </VRow>
        </VCardText>

        <VDivider />

        <VCardActions>
          <VSpacer />
          <VBtn color="primary" variant="text" @click="closeDialog">
            Cerrar
          </VBtn>
        </VCardActions>
      </VCard>

      <!-- Detail Dialog for ERROR Logs -->
      <VCard v-if="selectedLog && type === 'ERROR'">
        <VCardTitle class="d-flex align-center justify-space-between">
          <span>Detalles del Log de Error #{{ selectedLog.id }}</span>
          <VBtn icon="ri-close-line" variant="text" @click="closeDialog" />
        </VCardTitle>

        <VDivider />

        <VCardText class="pt-4">
          <VRow>
            <!-- Error Info -->
            <VCol cols="12">
              <h3 class="text-subtitle-1 mb-2">Información del Error</h3>
            </VCol>

            <VCol cols="12" md="6">
              <div class="text-caption text-medium-emphasis">Código de Error</div>
              <VChip color="error" size="small" class="mt-1">
                {{ selectedLog.errorCode }}
              </VChip>
            </VCol>

            <VCol cols="12" md="6">
              <div class="text-caption text-medium-emphasis">Clase de Excepción</div>
              <div class="text-body-2">{{ selectedLog.exceptionClassName || 'N/A' }}</div>
            </VCol>

            <VCol cols="12">
              <div class="text-caption text-medium-emphasis">Mensaje de Error</div>
              <VAlert type="error" variant="tonal" class="mt-1">
                {{ selectedLog.errorMessage }}
              </VAlert>
            </VCol>

            <VCol cols="12" v-if="selectedLog.details">
              <div class="text-caption text-medium-emphasis">Detalles</div>
              <div class="text-body-2">{{ selectedLog.details }}</div>
            </VCol>

            <!-- Request Info -->
            <VCol cols="12">
              <VDivider class="my-4" />
              <h3 class="text-subtitle-1 mb-2">Información de la Petición</h3>
            </VCol>

            <VCol cols="6" md="3">
              <div class="text-caption text-medium-emphasis">Método</div>
              <VChip :color="getMethodColor(selectedLog.httpMethod)" size="small" class="mt-1">
                {{ selectedLog.httpMethod }}
              </VChip>
            </VCol>

            <VCol cols="6" md="3">
              <div class="text-caption text-medium-emphasis">Fecha</div>
              <div class="text-body-2">{{ formatDate(selectedLog.timestamp) }}</div>
            </VCol>

            <VCol cols="12" md="6">
              <div class="text-caption text-medium-emphasis">Usuario</div>
              <div class="text-body-2">{{ selectedLog.userEmail || 'N/A' }}</div>
            </VCol>

            <VCol cols="12">
              <div class="text-caption text-medium-emphasis">URL</div>
              <div class="text-body-2 text-break">{{ selectedLog.requestUrl }}</div>
            </VCol>

            <VCol cols="12" md="6">
              <div class="text-caption text-medium-emphasis">IP Cliente</div>
              <div class="text-body-2">{{ selectedLog.clientIp }}</div>
            </VCol>

            <VCol cols="12" md="6">
              <div class="text-caption text-medium-emphasis">User ID</div>
              <div class="text-body-2">{{ selectedLog.userId || 'N/A' }}</div>
            </VCol>

            <VCol cols="12">
              <div class="text-caption text-medium-emphasis">User Agent</div>
              <div class="text-body-2 text-break">{{ selectedLog.userAgent }}</div>
            </VCol>

            <!-- Request Params -->
            <VCol cols="12" v-if="selectedLog.requestParams">
              <VDivider class="my-4" />
              <h3 class="text-subtitle-1 mb-2">Parámetros de la Petición</h3>
              <VCard variant="flat" class="bg-background">
                <VCardText>
                  <pre class="json-pre">{{ formatRequestParams(selectedLog.requestParams) }}</pre>
                </VCardText>
              </VCard>
            </VCol>

            <!-- Stack Trace -->
            <VCol cols="12" v-if="selectedLog.stackTrace">
              <VDivider class="my-4" />
              <h3 class="text-subtitle-1 mb-2">Stack Trace</h3>
              <VCard variant="flat" class="bg-background">
                <VCardText>
                  <pre class="stack-trace-pre">{{ selectedLog.stackTrace }}</pre>
                </VCardText>
              </VCard>
            </VCol>
          </VRow>
        </VCardText>

        <VDivider />

        <VCardActions>
          <VSpacer />
          <VBtn color="primary" variant="text" @click="closeDialog">
            Cerrar
          </VBtn>
        </VCardActions>
      </VCard>
    </VDialog>
  </VCard>
</template>

<style scoped>
.max-width-300 {
  max-width: 300px;
}

.json-pre {
  font-family: 'Courier New', Courier, monospace;
  font-size: 12px;
  line-height: 1.5;
  margin: 0;
  overflow-x: auto;
  white-space: pre-wrap;
  word-wrap: break-word;
}

.stack-trace-pre {
  font-family: 'Courier New', Courier, monospace;
  font-size: 11px;
  line-height: 1.4;
  margin: 0;
  overflow-x: auto;
  white-space: pre;
  max-height: 400px;
  overflow-y: auto;
}

.text-break {
  word-break: break-all;
}
</style>
