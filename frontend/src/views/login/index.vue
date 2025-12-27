<script setup lang="ts">
  import magnoLight from '@images/logos/magno-light.svg?raw'
</script>

<template>

  <div class="auth-wrapper d-flex align-md-end align-sm-center flex-md-column justify-center pa-4 login-bg">
    <formLogin />
    <VImg
      v-html=magnoLight
      class="position-absolute logo-login"
    />
  </div>
</template>
