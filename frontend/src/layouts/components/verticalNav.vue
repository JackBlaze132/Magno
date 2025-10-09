<template>
  <v-card >
    <v-layout class="auth-wrapper">
      <v-navigation-drawer class="bg-grey-lighten-2 position-fixed" permanent color="surface">
        <v-list nav>
          <v-list-item>
            <VIcon icon="ri-app-logo"></VIcon>
          </v-list-item>
          <v-list-item
            prepend-icon="ri-home-3-line"
            title="Inicio"
            value="inicio"
            to="/inicio">
          </v-list-item>
          <v-list-item
            prepend-icon="ri-file-chart-line"
            title="Informes"
            @click="openReportDialog"
            :active="false"
            link>
          </v-list-item>
          <VDivider class="px-4 my-4"/>
          <v-list-item
            prepend-icon="ri-calendar-check-fill"
            title="Periodos académicos"
            value="periodos"
            to="/periodos">
          </v-list-item>
          <v-list-item
            prepend-icon="ri-apps-line"
            title="Grupos de investigación"
            value="grupos"
            to="/grupos">
          </v-list-item>
          <v-list-item
            prepend-icon="ri-seedling-line"
            title="Semilleros"
            value="semilleros"
            to="/semilleros">
          </v-list-item>
          <v-divider class="px-4 my-4"></v-divider>
          <v-list-item
            prepend-icon="ri-user-line"
            title="Usuarios"
            value="usuarios"
            to="/usuarios">
          </v-list-item>
          <v-list-item
            prepend-icon="ri-shield-line"
            title="Roles"
            value="roles"
            to="/roles">
          </v-list-item>
          <v-list-item
            prepend-icon="ri-briefcase-4-line"
            title="Funcionarios"
            value="funcionarios"
            to="/funcionarios">
          </v-list-item>
          <v-list-item
            prepend-icon="ri-graduation-cap-line"
            title="Estudiantes"
            value="estudiantes"
            to="/estudiantes">
          </v-list-item>
          <v-divider class="px-4 my-4"></v-divider>
          <v-list-item
            prepend-icon="ri-external-link-line"
            title="Aliados externos"
            value="aliados"
            to="/aliados-externos">
          </v-list-item>
        </v-list>
        <template v-slot:append>
          <LogoutBtn/>
        </template>
      </v-navigation-drawer>
      <v-main></v-main>
    </v-layout>

    <!-- Report Creation Dialog -->
    <ReportCreationDialog
      v-model="showReportDialog"
      @report-created="onReportCreated"
      @close="showReportDialog = false"
    />
  </v-card>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { VDivider } from 'vuetify/components'
import ReportCreationDialog from '@/components/reports/ReportCreationDialog.vue'

// Reactive state for report dialog
const showReportDialog = ref(false)

// Methods
const openReportDialog = () => {
  showReportDialog.value = true
}

const onReportCreated = (reportData: any) => {
  console.log('Report created from navbar:', reportData)
  showReportDialog.value = false
  // Optionally navigate to reports page or show success message
}
</script>
