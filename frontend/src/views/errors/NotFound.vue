<script setup lang="ts">
import AppFooter from '@/components/AppFooter.vue'
import working from '@/assets/images/we_working.svg?raw'


</script>

<template>
  <div class="not-found-wrapper d-flex flex-column fill-height">
    <div class="not-found-container d-flex flex-column align-center justify-center flex-grow-1 pa-6 text-center">
      <div class="logo-container mb-1">
        <VIcon icon="ri-app-logo"></VIcon>
      </div>

      <div class="error-code-container mb-1">
        <h1 class="error-code">404</h1>
        <div class="error-divider"></div>
      </div>

      <h2 class="text-h4 font-weight-bold mb-1">Página no encontrada</h2>

      <p class="text-medium-emphasis mb-2 max-w-md">
        ¡Ups! Parece que te has perdido en el sistema. <br>
        La ruta que buscas no existe en Magno.
      </p>

      <!-- Placeholder for personal image -->
      <VCard
        variant="flat"
        class="mb-1 overflow-hidden rounded-xl working-card"
        max-width="500"
      >
        <div
          class="eder-svg-container d-flex align-center justify-center"
          v-html="working"
        ></div>
        <VCardText class="bg-primary-lighten-5 text-body-2 py-3 font-weight-medium">
          <VIcon icon="ri-tools-line" class="mr-2"/>
          No te preocupes, ¡estamos reparando el acceso!
        </VCardText>
      </VCard>

      <div class="d-flex flex-wrap justify-center gap-4 mb-35">

        <VBtn
          color="secondary"
          prepend-icon="ri-login-box-line"
          variant="outlined"
          size="large"
          rounded="lg"
          to="/inicio"
        >
          Ir al Inicio
        </VBtn>
      </div>
    </div>

    <AppFooter />
  </div>
</template>
<style scoped>
.eder-svg-container {
  color: rgb(var(--v-theme-on-surface));
}

.working-card {
  background-color: transparent !important;
}
</style>
