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
            prepend-icon="ri-file-chart-line"
            title="Informes"
            @click="handleReportClick"
            :active="false"
            link>
          </v-list-item>
          <VDivider class="px-4 my-4"/>
          <v-list-item
            prepend-icon="ri-calendar-check-fill"
            title="Periodos académicos"
            value="periodos"
            to="/periodos"
            @click="closeDrawerOnMobile">
          </v-list-item>
          <v-list-item
            prepend-icon="ri-apps-line"
            title="Grupos de investigación"
            value="grupos"
            to="/grupos"
            @click="closeDrawerOnMobile">
          </v-list-item>
          <v-list-item
            prepend-icon="ri-seedling-line"
            title="Semilleros"
            value="semilleros"
            to="/semilleros"
            @click="closeDrawerOnMobile">
          </v-list-item>
          <v-divider class="px-4 my-4"></v-divider>
          <v-list-item
            prepend-icon="ri-user-line"
            title="Usuarios"
            value="usuarios"
            to="/usuarios"
            @click="closeDrawerOnMobile">
          </v-list-item>
          <v-list-item
            prepend-icon="ri-shield-line"
            title="Roles"
            value="roles"
            to="/roles"
            @click="closeDrawerOnMobile">
          </v-list-item>
          <v-list-item
            prepend-icon="ri-briefcase-4-line"
            title="Funcionarios"
            value="funcionarios"
            to="/funcionarios"
            @click="closeDrawerOnMobile">
          </v-list-item>
          <v-list-item
            prepend-icon="ri-graduation-cap-line"
            title="Estudiantes"
            value="estudiantes"
            to="/estudiantes"
            @click="closeDrawerOnMobile">
          </v-list-item>
          <v-divider class="px-4 my-4"></v-divider>
          <v-list-item
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
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, onBeforeUnmount } from 'vue'
import { VDivider } from 'vuetify/components'
import ReportCreationDialog from '@/components/reports/ReportCreationDialog.vue'

// Reactive state
const showReportDialog = ref(false)
const drawer = ref(true)
const windowWidth = ref(window.innerWidth)

// Computed
const isMobile = computed(() => windowWidth.value < 960)

// Methods
const openReportDialog = () => {
  showReportDialog.value = true
}

const handleReportClick = () => {
  openReportDialog()
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
</script>
