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
            value="informes"
            to="/informes">
          </v-list-item>
          <VDivider class="px-4 my-4"/>
          <v-list-item
            prepend-icon="ri-calendar-check-fill"
            title="Periodos académicos"
            value="periodos"
            to="/periodos">
          </v-list-item>
          <v-list-item
            prepend-icon="ri-seedling-line"
            title="Semilleros"
            value="semilleros"
            to="/semilleros">
          </v-list-item>
          <v-divider class="px-4 my-4"></v-divider>
          <v-list-item
            prepend-icon="ri-user-settings-line"
            title="Usuarios"
            value="usuarios"
            to="/usuarios">
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
        </v-list>
        <template v-slot:append>
          <div class="pa-2">
            <v-btn
            color="black"
            block
            prepend-icon="ri-logout-box-line"
            >
              Cerrar sesión
            </v-btn>
          </div>
        </template>
      </v-navigation-drawer>
      <v-main></v-main>
    </v-layout>
  </v-card>
</template>

<script setup lang="ts">
  //import magnoDark from '@images/logos/magno-dark.svg?raw'
import { VDivider } from 'vuetify/components';
</script>
