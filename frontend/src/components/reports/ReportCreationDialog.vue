<template>
  <VDialog
    v-model="dialog"
    max-width="800px"
    persistent
    scrollable
  >
    <VCard>
      <VCardTitle class="d-flex align-center report-form-title">
        <VIcon class="me-2">ri-file-chart-line</VIcon>
        Crear Informe
        <VSpacer />
        <VBtn
          icon="ri-close-line"
          variant="text"
          @click="closeDialog"
        />
      </VCardTitle>

      <VDivider/>

      <VCardText class="pa-6">
        <VForm @submit.prevent="createReport" ref="reportForm">
          <!-- Report Type and Period Type Selection -->
          <VRow>
            <VCol cols="12" md="6">
              <VSelect
                v-model="reportData.reportType"
                :items="reportTypes"
                item-title="label"
                item-value="value"
                label="Tipo de Informe"
                variant="outlined"
                :rules="[(v: any) => !!v || 'Seleccione el tipo de Informe']"
                prepend-inner-icon="ri-file-chart-line"
                required
                @update:model-value="onReportTypeChange"
              />
            </VCol>

            <VCol cols="12" md="6">
              <VSelect
                v-model="reportData.periodType"
                :items="availablePeriodTypes"
                item-title="label"
                item-value="value"
                label="Tipo de Período"
                variant="outlined"
                :rules="[(v: any) => !!v || 'Seleccione el tipo de período']"
                prepend-inner-icon="ri-calendar-2-line"
                :disabled="!reportData.reportType || reportData.reportType === 'students-seedbeds'"
                required
                @update:model-value="onPeriodTypeChange"
              />
            </VCol>
          </VRow>

          <!-- Academic Period Selection -->
          <VRow>
            <VCol cols="12" md="6">
              <VSelect
                v-model="reportData.academicPeriodId"
                :items="filteredAcademicPeriods"
                item-title="name"
                item-value="id"
                :label="reportData.periodType === 'annual' ? 'Período Académico Inicial' : 'Período Académico'"
                variant="outlined"
                :rules="[(v: any) => !!v || (reportData.periodType === 'annual' ? 'Seleccione el período académico inicial' : 'Seleccione el período académico')]"
                prepend-inner-icon="ri-calendar-start"
                :loading="loadingPeriods"
                :disabled="!reportData.periodType && reportData.reportType !== 'students-seedbeds'"
                required
                @update:model-value="onFirstAcademicPeriodChange"
              />
            </VCol>
            <VCol cols="12" md="6">
              <VSelect
                v-model="reportData.academicPeriodId2"
                :items="filteredAcademicPeriods"
                item-title="name"
                item-value="id"
                :label="reportData.periodType === 'annual' ? 'Período Académico Final' : 'Período Académico'"
                variant="outlined"
                :rules="reportData.periodType === 'annual' ? [(v: any) => !!v || 'Seleccione el período académico final'] : []"
                prepend-inner-icon="ri-calendar-end"
                :loading="loadingPeriods"
                :disabled="!reportData.periodType || reportData.periodType !== 'annual'"
                required
                @update:model-value="onSecondAcademicPeriodChange"
              />
            </VCol>

          </VRow>

        </VForm>
      </VCardText>
      <VcardItem class="pe-5 ps-5"><VDivider/></VcardItem>

      <VCardItem class="d-flex justify-end">
        <LoadingBtn class="me-3"  text="Cancelar" :loading="loading" color="error" @click="closeDialog"/>
        <LoadingBtn icon="ri-file-chart-line" text="Generar Informe" :loading="loading" color="primary" @click="createReport" />
      </VCardItem>

    </VCard>
  </VDialog>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import API from '@/utils/api'

interface ReportData {
  reportType: 'investigation-group' | 'students-seedbeds' | 'active-seedbeds' | null
  periodType: 'single' | 'annual' | null
  academicPeriodId: number | string | null
  academicPeriodId2?: number | string | null
}

export default defineComponent({
  name: 'ReportCreationDialog',
  emits: ['report-created', 'close'],

  props: {
    modelValue: {
      type: Boolean,
      default: false
    }
  },

  data() {
    return {
      dialog: this.modelValue,
      loading: false,
      loadingPeriods: false,
      academicPeriods: [] as any[],

      reportData: {
        reportType: null,
        periodType: null,
        academicPeriodId: null,
        academicPeriodId2: null
      } as ReportData,

      reportTypes: [
        { label: 'Grupo de Investigación', value: 'investigation-group' },
        { label: 'Estudiantes en Semilleros', value: 'students-seedbeds' },
        { label: 'Semilleros Activos', value: 'active-seedbeds' }
      ],

      periodTypes: [
        { label: 'Periodo único', value: 'single' },
        { label: 'Consolidado', value: 'annual' }
      ]
    }
  },

  computed: {
    availablePeriodTypes() {
      // All report types can be either single or annual now
      return this.periodTypes
    },

    filteredAcademicPeriods() {
      // For both single and annual reports, show all individual academic periods
      // Users can select any two periods for consolidated reports
      return this.academicPeriods
    }
  },

  watch: {
    modelValue: {
      handler(newVal: boolean) {
        this.dialog = newVal
        if (newVal) {
          this.loadAcademicPeriods()
        }
      },
      immediate: true
    },

    'reportData.academicPeriodId': {
      handler() {
        // No longer need to load targets since we removed target selection
      }
    }
  },

  methods: {
    onReportTypeChange() {
      // Reset dependent fields when report type changes
      this.reportData.periodType = null
      this.reportData.academicPeriodId = null
      this.reportData.academicPeriodId2 = null

      // Special handling for students in seedbeds - always use single period
      if (this.reportData.reportType === 'students-seedbeds') {
        this.reportData.periodType = 'single'
      }
    },

    onPeriodTypeChange() {
      // Reset academic period when period type changes (since options change)
      this.reportData.academicPeriodId = null
      this.reportData.academicPeriodId2 = null
    },

    closeDialog() {
      this.dialog = false
      this.$emit('close')
      this.resetForm()
    },

    resetForm() {
      this.reportData = {
        reportType: null,
        periodType: null,
        academicPeriodId: null,
        academicPeriodId2: null
      }
    },

    async loadAcademicPeriods() {
      this.loadingPeriods = true
      try {
        const headers = { 'API-VERSION': '1' }
        const periods = await API.get(API.ACADEMIC_PERIODS, headers)
        this.academicPeriods = periods || []
      } catch (error) {
        console.error('Error loading academic periods:', error)
        console.error('Error al cargar los períodos académicos:', error)
      } finally {
        this.loadingPeriods = false
      }
    },

    onFirstAcademicPeriodChange(value: any) {
      // Handle first academic period selection changes
      this.reportData.academicPeriodId = value
    },

    onSecondAcademicPeriodChange(value: any) {
      // Handle second academic period selection changes
      this.reportData.academicPeriodId2 = value
    },

    onAcademicPeriodChange(value: any) {
      // Handle academic period selection changes (legacy method)
      this.reportData.academicPeriodId = value
    },

    async createReport() {
      const form = this.$refs.reportForm as any
      const isValid = await form?.validate()

      if (!isValid) {
        console.warn('Por favor complete todos los campos requeridos')
        return
      }

      this.loading = true
      try {

        // Determine the correct endpoint and parameters based on report type and period type
        let endpoint = ''
        let params = ''

        if (this.reportData.reportType === 'students-seedbeds') {
          // Students in seedbeds uses rspId (research seedbed ID) and apId (academic period ID) parameters
          endpoint = API.ANUAL_REPORTS_RESEARCH_SEEDBEDS_STUDENTS
          // TODO: Determine what rspId should be - currently using academicPeriodId as placeholder
          // rspId might be a research seedbed profile ID that needs to be selected or derived
          const rspId = this.reportData.academicPeriodId // TODO: Replace with correct rspId source
          params = `?rspId=${rspId}&apId=${this.reportData.academicPeriodId}`

        } else if (this.reportData.periodType === 'single') {
          // single reports use ?apId parameter
          if (this.reportData.reportType === 'investigation-group') {
            endpoint = API.SINGLE_PERIOD_REPORTS_INVESTIGATION_GROUPS
          } else if (this.reportData.reportType === 'active-seedbeds') {
            endpoint = API.SINGLE_PERIOD_REPORTS_ACTIVE_RESEARCH_SEEDBEDS
          }
          params = `?apId=${this.reportData.academicPeriodId}`

        } else if (this.reportData.periodType === 'annual') {
          // Annual reports use ?apId1 and ?apId2 parameters
          if (this.reportData.reportType === 'investigation-group') {
            endpoint = API.CONSOLIDATE_REPORTS_INVESTIGATION_GROUPS
          } else if (this.reportData.reportType === 'active-seedbeds') {
            endpoint = API.CONSOLIDATE_REPORTS_ACTIVE_RESEARCH_SEEDBEDS
          }

          // For consolidated reports, use the two separate academic period fields
          if (!this.reportData.academicPeriodId2) {
            throw new Error('Debe seleccionar el período académico final para Informes consolidados')
          }

          params = `?apId1=${this.reportData.academicPeriodId}&apId2=${this.reportData.academicPeriodId2}`
        }

        if (!endpoint) {
          throw new Error('Tipo de Informe no válido')
        }
         const headers = { 'API-VERSION': '1' }
        // Make the API call and handle blob response
        const fullEndpoint = `${endpoint}${params}`
        const response = await API.download(fullEndpoint, headers)

        if (!response.ok) {
          throw new Error(`Error al generar el Informe: ${response.status}`)
        }

        // Handle the Excel file download
        const blob = await response.blob()
        const url = window.URL.createObjectURL(blob)

        // Generate filename based on report type and period type
        let filename = ''
        const timestamp = new Date().toISOString().split('T')[0] // YYYY-MM-DD format

        if (this.reportData.reportType === 'investigation-group') {
          filename = `Informe_Grupos_Investigacion_${this.reportData.periodType === 'annual' ? 'Consolidado' : 'Periodo único'}_${timestamp}.xlsx`
        } else if (this.reportData.reportType === 'students-seedbeds') {
          filename = `Informe_Estudiantes_Semilleros_${this.reportData.periodType === 'annual' ? 'Consolidado' : 'Periodo único'}_${timestamp}.xlsx`
        } else if (this.reportData.reportType === 'active-seedbeds') {
          filename = `Informe_Semilleros_Activos_${this.reportData.periodType === 'annual' ? 'Consolidado' : 'Periodo único'}_${timestamp}.xlsx`
        }

        // Create download link and trigger download
        const a = document.createElement('a')
        a.href = url
        a.download = filename
        document.body.appendChild(a)
        a.click()
        a.remove()

        // Clean up the URL object
        window.URL.revokeObjectURL(url)

        console.log('Informe descargado exitosamente')
        this.$emit('report-created', { success: true, filename })
        this.closeDialog()

      } catch (error) {
        console.error('Error creating report:', error)
        console.error('Error al crear el Informe:', error)
      } finally {
        this.loading = false
      }
    }
  }
})
</script>
