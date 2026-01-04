<template>
  <div>
    <v-navigation-drawer
      v-model="drawer"
      class="bg-grey-lighten-2 nav-drawer"
      :rail="false"
      :permanent="!isMobile"
      :temporary="isMobile"
      color="surface"
      width="256"
    >
      <v-list nav>
        <v-list-item>
          <VIcon icon="ri-app-logo"></VIcon>
        </v-list-item>
          <v-list-item
            prepend-icon="ri-home-3-line"
            title="Inicio"
            value="inicio"
            to="/inicio"
            @click="closeDrawerOnMobile">
          </v-list-item>
          <v-list-item
            v-if="authStore.can('view', 'report')"
            prepend-icon="ri-file-chart-line"
            title="Informes"
            @click="handleReportClick"
            :active="false"
            link>
          </v-list-item>
        <v-list-item
          v-if="authStore.can('view', 'certificate')"
          prepend-icon="ri-verified-badge-line"
          title="Certificados"
          @click="handleCertClick"
          :active="false"
          link>
        </v-list-item>
          <VDivider class="px-4 my-4"/>
          <v-list-item
            v-if="authStore.can('view', 'period')"
            prepend-icon="ri-calendar-check-fill"
            title="Periodos académicos"
            value="periodos"
            to="/periodos"
            @click="closeDrawerOnMobile">
          </v-list-item>
          <v-list-item
            v-if="authStore.can('view', 'group')"
            prepend-icon="ri-apps-line"
            title="Grupos de investigación"
            value="grupos"
            to="/grupos"
            @click="closeDrawerOnMobile">
          </v-list-item>
          <v-list-item
            v-if="authStore.can('view', 'seedbed')"
            prepend-icon="ri-seedling-line"
            title="Semilleros"
            value="semilleros"
            to="/semilleros"
            @click="closeDrawerOnMobile">
          </v-list-item>
          <v-divider
            v-if="authStore.can('view', 'user_integra') || authStore.can('view', 'role') || authStore.can('view', 'user_diri') || authStore.can('view', 'functionary_profile') || authStore.can('view', 'student_profile')"
            class="px-4 my-4"
          ></v-divider>
          <v-list-item
            v-if="authStore.can('view', 'user_integra')"
            prepend-icon="ri-user-line"
            title="Usuarios"
            value="usuarios"
            to="/usuarios"
            @click="closeDrawerOnMobile">
          </v-list-item>
          <v-list-item
            v-if="authStore.can('view', 'role')"
            prepend-icon="ri-shield-line"
            title="Roles"
            value="roles"
            to="/roles"
            @click="closeDrawerOnMobile">
          </v-list-item>
          <v-list-item
            v-if="authStore.can('view', 'user_diri')"
            prepend-icon="ri-lock-line"
            title="DIRI"
            value="diri"
            to="/diri"
            @click="closeDrawerOnMobile">
          </v-list-item>
          <v-list-item
            v-if="authStore.can('view', 'functionary_profile')"
            prepend-icon="ri-briefcase-4-line"
            title="Funcionarios"
            value="funcionarios"
            to="/funcionarios"
            @click="closeDrawerOnMobile">
          </v-list-item>
          <v-list-item
            v-if="authStore.can('view', 'student_profile')"
            prepend-icon="ri-graduation-cap-line"
            title="Estudiantes"
            value="estudiantes"
            to="/estudiantes"
            @click="closeDrawerOnMobile">
          </v-list-item>
          <v-divider
            v-if="authStore.can('view', 'user_external')"
            class="px-4 my-4"
          ></v-divider>
          <v-list-item
            v-if="authStore.can('view', 'user_external')"
            prepend-icon="ri-external-link-line"
            title="Aliados externos"
            value="aliados"
            to="/aliados-externos"
            @click="closeDrawerOnMobile">
          </v-list-item>
        </v-list>
        <template v-slot:append>
          <LogoutBtn/>
        </template>
      </v-navigation-drawer>

    <!-- Report Creation Dialog -->
    <ReportCreationDialog
      v-model="showReportDialog"
      @report-created="onReportCreated"
      @close="showReportDialog = false"
    />

    <!-- Certificate Creation Dialog -->
    <CertificateCreationDialog
      v-model="showCertDialog"
      @certificate-created="onCertCreated"
      @close="showCertDialog = false"
    />
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, onBeforeUnmount } from 'vue'
import { VDivider } from 'vuetify/components'
import ReportCreationDialog from '@/components/reports/ReportCreationDialog.vue'
import { useAuthStore } from '@/stores/authStore'

const authStore = useAuthStore()

// Reactive state
const showReportDialog = ref(false)
const showCertDialog = ref(false)
const drawer = ref(true)
const windowWidth = ref(window.innerWidth)

// Computed
const isMobile = computed(() => windowWidth.value < 960)

// Methods
const openReportDialog = () => {
  showReportDialog.value = true
}

const openCertDialog = () => {
  showCertDialog.value = true
}

const handleReportClick = () => {
  openReportDialog()
  closeDrawerOnMobile()
}
const handleCertClick = () => {
  openCertDialog()
  closeDrawerOnMobile()
}

const closeDrawerOnMobile = () => {
  if (isMobile.value) {
    drawer.value = false
  }
}

const toggleDrawer = () => {
  console.log('toggleDrawer called, current drawer state:', drawer.value)
  drawer.value = !drawer.value
  console.log('New drawer state:', drawer.value)
}

const handleResize = () => {
  windowWidth.value = window.innerWidth
}

// Lifecycle
onMounted(() => {
  window.addEventListener('resize', handleResize)
  // On mobile, start with drawer closed
  if (isMobile.value) {
    drawer.value = false
  }
})

onBeforeUnmount(() => {
  window.removeEventListener('resize', handleResize)
})

// Expose toggleDrawer for parent
defineExpose({ toggleDrawer })

const onReportCreated = (reportData: any) => {
  console.log('Report created from navbar:', reportData)
  showReportDialog.value = false
  // Optionally navigate to reports page or show success message
}

const onCertCreated = (certData: any) => {
  console.log('Certificate created from navbar:', certData)
  showCertDialog.value = false
  // Optionally navigate to certificates page or show success message
}
</script>
