<template>
  <IconBtn @click="changeTheme">
    <VIcon :icon="props.themes[currentThemeIndex].icon" />
    <VTooltip
      activator="parent"
      open-delay="1000"
      scroll-strategy="close"
    >
      <span class="text-capitalize">{{ currentThemeName }}</span>
    </VTooltip>
  </IconBtn>
</template>


<script setup lang="ts">
import { useTheme } from 'vuetify'
import type { ThemeSwitcherTheme } from '@layouts/scripts/types'
import { useCycleList } from '@vueuse/core'
import { watch, onMounted } from 'vue';

const props = defineProps<{
  themes: ThemeSwitcherTheme[]
}>()

const { name: themeName, global: globalTheme } = useTheme()
const { state: currentThemeName, next: getNextThemeName, index: currentThemeIndex } = useCycleList(props.themes.map(t => t.name), { initialValue: themeName })

const changeTheme = () => {
  const nextTheme = getNextThemeName()
  globalTheme.name.value = nextTheme
  localStorage.setItem('selectedTheme', nextTheme) // Guardar en localStorage
}

// Update icon if theme is changed from other sources
watch(() => globalTheme.name.value, val => {
  currentThemeName.value = val
})

// Load theme from localStorage on component mount
onMounted(() => {
  const savedTheme = localStorage.getItem('selectedTheme')
  if (savedTheme) {
    globalTheme.name.value = savedTheme
    currentThemeName.value = savedTheme
  }
})
</script>
