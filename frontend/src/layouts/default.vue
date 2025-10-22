<template>
  <div class="d-flex flex-column layout-wrapper">
    <!-- Mobile Header (only visible on mobile) -->
    <v-app-bar
      v-if="isMobile"

      density="compact"
      elevation="2"
      class="mobile-header"
    >
      <v-app-bar-nav-icon @click="toggleNav"></v-app-bar-nav-icon>

      <v-spacer></v-spacer>
      <AppThemeSwitcher />
      <ProfilePicture />
    </v-app-bar>

    <!-- Main Content -->
    <div class="d-flex flex-grow-1 position-relative" :class="{ 'mobile-layout': isMobile }">
      <verticalNav ref="navDrawer" />
      <div :class="contentClass">
        <RouterView />
      </div>

      <!-- Desktop controls (hidden on mobile) -->
      <div v-if="!isMobile" class="desktop-controls">
        <AppThemeSwitcher class="ma-1" />
        <ProfilePicture class="ma-1" />
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, computed, onMounted, onBeforeUnmount } from 'vue'
import AppThemeSwitcher from './components/appThemeSwitcher.vue'
import verticalNav from './components/verticalNav.vue'
import ProfilePicture from './components/profilePicture.vue'

const navDrawer = ref<any>(null)
const windowWidth = ref(window.innerWidth)

const isMobile = computed(() => windowWidth.value < 960)
const contentClass = computed(() =>
  isMobile.value ? 'ma-4 flex-grow-1 content-mobile' : 'content-desktop'
)

const toggleNav = () => {
  console.log('Hamburger clicked, navDrawer:', navDrawer.value)
  if (navDrawer.value?.toggleDrawer) {
    navDrawer.value.toggleDrawer()
  } else {
    console.error('toggleDrawer method not found')
  }
}

const handleResize = () => {
  windowWidth.value = window.innerWidth
}

onMounted(() => {
  window.addEventListener('resize', handleResize)
})

onBeforeUnmount(() => {
  window.removeEventListener('resize', handleResize)
})
</script>

