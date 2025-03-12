<template>
  <themeSwitcher :themes="themes" />
</template>

<script setup lang="ts">
import type { ThemeSwitcherTheme } from '@layouts/scripts/types'
import themeSwitcher from './themeSwitcher.vue';

const themes: ThemeSwitcherTheme[] = [
  {
    name: 'light',
    icon: 'ri-sun-line',
  },
  {
    name: 'dark',
    icon: 'ri-moon-clear-line',
  },
]
</script>


