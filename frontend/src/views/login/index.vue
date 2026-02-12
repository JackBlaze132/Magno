<script setup lang="ts">
  import AppFooter from '@/components/AppFooter.vue'
  import magnoLogo from '@images/logos/magno.svg?raw'
</script>

<template>
  <div class="login-wrapper">
    <!-- Desktop Corner Logo -->
    <div class="top-left-logo d-none d-md-block" v-html="magnoLogo"></div>

    <div class="auth-wrapper d-flex align-md-end align-center flex-column justify-center pa-4 login-bg">
      <!-- Mobile Logo - Above Form -->
      <div class="mobile-logo d-md-none mb-4" v-html="magnoLogo"></div>

      <formLogin class="login-form-container" />
    </div>
    <AppFooter />
  </div>
</template>
