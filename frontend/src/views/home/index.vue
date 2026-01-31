<template>
  <VContainer fluid class="px-2 px-sm-6 py-4 py-sm-6">
    <VRow>
      <VCol cols="12">
        <VCard class="pa-6" variant="flat">
          <!-- Desktop Header: Title and Chip on the same row, Chip at max right -->
          <div class="d-none d-sm-flex align-center pa-4">
            <VIcon class="me-2">ri-home-line</VIcon>
            <VCardTitle class="pa-0">Bienvenido a Magno</VCardTitle>
            <VSpacer />
            <VChip
              color="primary"
              variant="elevated"
              class="user-name-chip"
            >
              {{ authStore.userName }}
            </VChip>
          </div>

          <!-- Mobile Header: Title and Chip on different rows -->
          <div class="d-flex d-sm-none flex-column">
            <VCardItem class="welcome-title">
              <template v-slot:prepend>
                <VIcon class="me-2">ri-home-line</VIcon>
              </template>
              <VCardTitle class="pa-0">Bienvenido a Magno</VCardTitle>
            </VCardItem>
            <VCardItem class="pt-0 welcome-title">
              <VChip
                color="primary"
                variant="elevated"
                class="user-name-chip"
              >
                {{ authStore.userName }}
              </VChip>
            </VCardItem>
          </div>

          <VCardText class="welcome-message">
            Gestiona tus semilleros de investigación y genera certificados fácilmente.
          </VCardText>
        </VCard>
      </VCol>
    </VRow>
    <VRow class="mb-4">
      <!-- Certificate Generation for Students -->
      <VCol v-if="authStore.isStudent" cols="12" sm="6" md="4">
        <VCard class="pa-4 text-center shortcut-card" variant="flat" @click="openCertificateDialog">
          <VCardTitle>
            <VIcon size="48" color="primary">ri-verified-badge-line</VIcon>
          </VCardTitle>
          <VCardText>
            <h3>Generar Certificado</h3>
            <p>Crea certificados para tus semilleros asignados.</p>
          </VCardText>
        </VCard>
      </VCol>

      <!-- Report Generation for Non-Students -->
      <VCol v-else cols="12" sm="6" md="4">
        <VCard class="pa-4 text-center shortcut-card" variant="flat" @click="openReportDialog">
          <VCardTitle>
            <VIcon size="48" color="primary">ri-file-chart-line</VIcon>
          </VCardTitle>
          <VCardText>
            <h3>Generar Informe</h3>
            <p>Crea informes y reportes de los semilleros.</p>
          </VCardText>
        </VCard>
      </VCol>

      <VCol cols="12" sm="6" md="4">
        <VCard class="pa-4 text-center shortcut-card" variant="flat" @click="navigateToSeedbeds">
          <VCardTitle>
            <VIcon size="48" color="success">ri-seedling-line</VIcon>
          </VCardTitle>
          <VCardText>
            <h3>Mis Semilleros</h3>
            <p>Visualiza y administra tus semilleros de investigación.</p>
          </VCardText>
        </VCard>
      </VCol>

      <VCol cols="12" sm="6" md="4">
        <VCard class="pa-4 text-center shortcut-card" variant="flat" @click="navigateToProfile">
          <VCardTitle>
            <VIcon size="48" color="info">ri-account-box-line</VIcon>
          </VCardTitle>
          <VCardText>
            <h3>Perfil de Usuario</h3>
            <p>Actualiza tu información personal.</p>
          </VCardText>
        </VCard>
      </VCol>
    </VRow>

    <!-- Quick Stats Section -->
    <div v-if="authStore.can('view', 'dashboard')">
      <VRow>
        <VCol v-for="stat in stats" :key="stat.title" cols="12" sm="6" md="3">
          <VCard class="pa-4 h-100 stat-card" variant="flat" process_messages>
            <div class="d-flex align-center">
              <VAvatar :color="stat.color" variant="tonal" size="48" class="me-4">
                <VIcon :icon="stat.icon" size="24" />
              </VAvatar>
              <div>
                <div class="text-caption text-uppercase font-weight-bold">{{ stat.title }}</div>
                <div class="text-h5 font-weight-bold">
                  <VProgressCircular v-if="loadingStats" indeterminate size="20" width="2" color="primary" />
                  <span v-else>{{ stat.value }}</span>
                </div>
              </div>
            </div>
          </VCard>
        </VCol>
      </VRow>

      <!-- Dashboard Section: Filters and Sex Distribution -->
      <VRow>
        <VCol cols="12" md="4">
          <VCard class="pa-4 h-100" variant="flat">
            <VCardTitle class="d-flex align-center px-0">
              <VIcon class="me-2" color="primary">ri-filter-3-line</VIcon>
              Filtros de Distribución
            </VCardTitle>
            <VCardText class="px-0 pt-4">
              <VSelect
                v-model="selectedPeriod"
                :items="periods"
                item-title="name"
                item-value="id"
                label="Periodo Académico"
                variant="outlined"
                density="compact"
                class="mb-4"
                clearable
                @update:model-value="onPeriodChange"
              ></VSelect>
              <VSelect
                v-model="selectedInvestigationGroup"
                :items="investigationGroups"
                item-title="name"
                item-value="id"
                :label="selectedPeriod ? 'Seleccionar Grupo de Investigación' : 'Primero seleccione un periodo'"
                variant="outlined"
                density="compact"
                class="mb-4"
                clearable
                :disabled="!selectedPeriod"
                :hint="!selectedPeriod ? 'Debe seleccionar un periodo académico primero' : ''"
                persistent-hint
                @update:model-value="onGroupChange"
              ></VSelect>
              <VSelect
                v-model="selectedSeedbed"
                :items="seedbeds"
                item-title="name"
                item-value="id"
                :label="selectedInvestigationGroup ? 'Seleccionar Semillero' : 'Primero seleccione un grupo'"
                variant="outlined"
                density="compact"
                clearable
                :disabled="!selectedInvestigationGroup"
                :hint="!selectedInvestigationGroup ? 'Debe seleccionar un grupo de investigación primero' : ''"
                persistent-hint
                @update:model-value="fetchSexData"
              ></VSelect>
            </VCardText>
          </VCard>
        </VCol>

        <VCol cols="12" md="8">
          <VCard class="pa-4 h-100" variant="flat">
            <VCardTitle class="d-flex align-center px-0">
              <VIcon class="me-2" color="primary">ri-pie-chart-2-line</VIcon>
              Distribución por Sexo
            </VCardTitle>
            <VCardText class="d-flex flex-column flex-sm-row align-center justify-space-around py-6">
              <div v-if="loadingSexData" class="text-center">
                <VProgressCircular indeterminate color="primary"></VProgressCircular>
                <div class="mt-2">Cargando datos...</div>
              </div>
              <template v-else-if="hasSexData">
                <div class="text-center mb-4 mb-sm-0">
                  <VProgressCircular
                    :model-value="malePercentage"
                    :size="120"
                    :width="15"
                    color="info"
                  >
                    {{ malePercentage }}%
                  </VProgressCircular>
                  <div class="mt-2 font-weight-bold">Masculino</div>
                  <div class="text-caption">{{ maleCount }} estudiantes</div>
                </div>
                <div class="text-center">
                  <VProgressCircular
                    :model-value="femalePercentage"
                    :size="120"
                    :width="15"
                    color="error"
                  >
                    {{ femalePercentage }}%
                  </VProgressCircular>
                  <div class="mt-2 font-weight-bold">Femenino</div>
                  <div class="text-caption">{{ femaleCount }} estudiantes</div>
                </div>
              </template>
              <div v-else class="text-center text-grey">
                <VIcon size="48" class="mb-2">ri-information-line</VIcon>
                <div>Selecciona un semillero para ver la distribución</div>
              </div>
            </VCardText>
          </VCard>
        </VCol>
      </VRow>

      <!-- Trends Section -->
      <VRow class="mb-1">
        <VCol cols="12">
          <VCard class="pa-4" variant="flat">
            <VCardTitle class="d-flex align-center px-0">
              <VIcon class="me-2" color="primary">ri-line-chart-line</VIcon>
              Tendencia de Estudiantes por Periodo
            </VCardTitle>
            <VCardText class="px-0 pt-4 chart-container">
              <div v-if="loadingTrendData" class="d-flex align-center justify-center h-100">
                <VProgressCircular indeterminate color="primary"></VProgressCircular>
              </div>
              <BarChart
                v-else
                :data="chartData"
                :options="chartOptions"
              />
            </VCardText>
          </VCard>
        </VCol>
      </VRow>
    </div>



    <!-- Certificate Creation Dialog -->
    <CertificateCreationDialog
      v-model="showCertificateDialog"
      @certificate-created="onCertificateCreated"
      @close="showCertificateDialog = false"
    />

    <!-- Report Creation Dialog -->
    <ReportCreationDialog
      v-model="showReportDialog"
      @report-created="onReportCreated"
      @close="showReportDialog = false"
    />
  </VContainer>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import { useAuthStore } from '@/stores/authStore'
import API from '@/utils/api'
import CertificateCreationDialog from '@/components/certificates/CertificateCreationDialog.vue'
import ReportCreationDialog from '@/components/reports/ReportCreationDialog.vue'
import { Bar } from 'vue-chartjs'
import { Chart as ChartJS, Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale } from 'chart.js'

const authStore = useAuthStore()
ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale)

export default defineComponent({
  name: 'HomeView',
  components: {
    CertificateCreationDialog,
    ReportCreationDialog,
    BarChart: Bar
  },
  data() {
    return {
      showCertificateDialog: false,
      showReportDialog: false,
      loadingStats: true,
      loadingSexData: false,
      loadingTrendData: true,
      selectedPeriod: null as number | null,
      selectedInvestigationGroup: null as number | null,
      selectedSeedbed: null as number | null,
      periods: [] as any[],
      investigationGroups: [] as any[],
      investigationGroupProfiles: [] as any[],
      seedbeds: [] as any[],
      maleCount: 0,
      femaleCount: 0,
      chartData: {
        labels: [],
        datasets: []
      } as any,
      chartOptions: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          legend: {
            display: false
          }
        },
        scales: {
          y: {
            beginAtZero: true,
            ticks: {
              stepSize: 1
            }
          }
        }
      },
      stats: [
        { title: 'Periodos', value: 0, icon: 'ri-calendar-line', color: 'primary' },
        { title: 'Grupos', value: 0, icon: 'ri-team-line', color: 'success' },
        { title: 'Semilleros', value: 0, icon: 'ri-seedling-line', color: 'warning' },
        { title: 'Estudiantes', value: 0, icon: 'ri-user-star-line', color: 'info' }
      ]
    }
  },
  computed: {
    authStore() {
      return useAuthStore()
    },
    hasSexData(): boolean {
      return this.maleCount > 0 || this.femaleCount > 0
    },
    malePercentage(): number {
      const total = this.maleCount + this.femaleCount
      return total > 0 ? Math.round((this.maleCount / total) * 100) : 0
    },
    femalePercentage(): number {
      const total = this.maleCount + this.femaleCount
      return total > 0 ? Math.round((this.femaleCount / total) * 100) : 0
    }
  },
  mounted() {
    this.fetchStats()
    this.fetchInitialData()
    this.fetchTrendData()
  },
  methods: {
    async fetchTrendData() {
      this.loadingTrendData = true
      const headers = { "API-VERSION": "1" }
      try {
        const periods = await API.get(API.ACADEMIC_PERIODS, headers)
        // Take last 5 periods for the trend
        const recentPeriods = periods.slice(-5)

        const labels = recentPeriods.map((p: any) => p.name)
        const data = await Promise.all(
          recentPeriods.map(async (p: any) => {
            const students = await API.get(API.STUDENT_PROFILES_BY_ACADEMIC_PERIOD + p.id, headers)
            return students.length
          })
        )

        this.chartData = {
          labels,
          datasets: [
            {
              label: 'Estudiantes',
              backgroundColor: '#1867C0',
              data
            }
          ]
        }
      } catch (error) {
        console.error('Error fetching trend data:', error)
      } finally {
        this.loadingTrendData = false
      }
    },
    async fetchInitialData() {
      const headers = { "API-VERSION": "1" }
      try {
        const periods = await API.get(API.ACADEMIC_PERIODS, headers)
        this.periods = periods

        // Set default period if available
        const currentPeriod = periods.find((p: any) => p.is_current)
        if (currentPeriod) {
          this.selectedPeriod = currentPeriod.id
          await this.fetchGroupsForPeriod()
        }
      } catch (error) {
        console.error('Error fetching initial data:', error)
      }
    },
    async fetchGroupsForPeriod() {
      if (!this.selectedPeriod) {
        this.investigationGroups = []
        this.investigationGroupProfiles = []
        return
      }
      const headers = { "API-VERSION": "1" }
      try {
        const profiles = await API.get(API.INVESTIGATION_GROUPS_PROFILES_BY_ACADEMIC_PERIOD + this.selectedPeriod, headers)
        this.investigationGroupProfiles = profiles

        // Extract unique groups from profiles
        const groupsMap = new Map()
        profiles.forEach((p: any) => {
          if (p.investigation_group) {
            groupsMap.set(p.investigation_group.id, p.investigation_group)
          }
        })
        this.investigationGroups = Array.from(groupsMap.values())
      } catch (error) {
        console.error('Error fetching groups for period:', error)
      }
    },
    async fetchSeedbedsForGroup() {
      if (!this.selectedInvestigationGroup || !this.selectedPeriod) {
        this.seedbeds = []
        return
      }

      // Find the profile ID for the selected group in the selected period
      const profile = this.investigationGroupProfiles.find(
        (p: any) => p.investigation_group?.id === this.selectedInvestigationGroup
      )

      if (!profile) {
        this.seedbeds = []
        return
      }

      const headers = { "API-VERSION": "1" }
      try {
        const seedbedProfiles = await API.get(API.RESEARCH_SEEDBEDS_PROFILES_BY_INVESTIGATION_GROUP_PROFILE + profile.id, headers)

        // Map profiles to items for the selector, using the profile ID as the value
        this.seedbeds = seedbedProfiles.map((sp: any) => ({
          id: sp.id, // This is the research_seedbed_profile ID
          name: sp.research_seedbed?.name || 'Sin nombre'
        }))
      } catch (error) {
        console.error('Error fetching seedbeds for group:', error)
      }
    },
    async fetchSexData() {
      if (!this.selectedSeedbed) {
        this.maleCount = 0
        this.femaleCount = 0
        return
      }

      this.loadingSexData = true
      const headers = { "API-VERSION": "1" }
      try {
        // Use the endpoint that filters by research seedbed profile ID
        // This uniquely identifies the (Period, Group, Seedbed) combination
        const members = await API.get(API.RESEARCH_SEEDBED_STUDENT_PROFILES + this.selectedSeedbed, headers)

        let males = 0
        let females = 0

        members.forEach((member: any) => {
          const sex = member.student_profile?.user?.sex
          if (sex === 'MASCULINO') males++
          else if (sex === 'FEMENINO') females++
        })

        this.maleCount = males
        this.femaleCount = females
      } catch (error) {
        console.error('Error fetching sex data:', error)
      } finally {
        this.loadingSexData = false
      }
    },
    async onPeriodChange() {
      this.selectedInvestigationGroup = null
      this.selectedSeedbed = null
      this.maleCount = 0
      this.femaleCount = 0
      this.seedbeds = []
      await this.fetchGroupsForPeriod()
    },
    async onGroupChange() {
      this.selectedSeedbed = null
      this.maleCount = 0
      this.femaleCount = 0
      await this.fetchSeedbedsForGroup()
    },
    async fetchStats() {
      const headers = {
        "API-VERSION": "1"
      }
      this.loadingStats = true
      try {
        const studentEndpoint = this.authStore.currentAcademicPeriod
          ? API.STUDENT_PROFILES_BY_ACADEMIC_PERIOD + this.authStore.currentAcademicPeriod
          : API.USERS_STUDENTS

        const [periods, groups, seedbeds, students] = await Promise.all([
          API.get(API.ACADEMIC_PERIODS, headers),
          API.get(API.INVESTIGATION_GROUPS, headers),
          API.get(API.RESEARCH_SEEDBEDS, headers),
          API.get(studentEndpoint, headers)
        ])

        console.log("periodos:", periods)
        this.stats[0].value = periods.length
        this.stats[1].value = groups.length
        this.stats[2].value = seedbeds.length
        this.stats[3].value = students.length
      } catch (error) {
        console.error('Error fetching stats:', error)
      } finally {
        this.loadingStats = false
      }
    },
    openCertificateDialog() {
      this.showCertificateDialog = true
    },
    openReportDialog() {
      this.showReportDialog = true
    },
    navigateToSeedbeds() {
      this.$router.push('/semilleros') // Adjust route as needed
    },
    navigateToProfile() {
      this.$router.push('/perfil') // Adjust route as needed
    },
    onCertificateCreated(data: any) {
      console.log('Certificate created:', data)
      // Handle post-creation logic, e.g., refresh data
    },
    onReportCreated(data: any) {
      console.log('Report created:', data)
      // Handle post-creation logic, e.g., refresh data
    }
  }
})
</script>
