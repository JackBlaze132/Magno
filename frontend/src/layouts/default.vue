<template>
  <div class="d-flex">
    <verticalNav/>
    <RouterView class="ma-8 w-75"/>
    <AppThemeSwitcher class="ma-1"/>
    <ProfilePicture class="ma-1"/>
</div>
</template>

<script lang="ts" setup>
import AppThemeSwitcher from './components/appThemeSwitcher.vue';
import verticalNav from './components/verticalNav.vue';
import ProfilePicture from './components/profilePicture.vue';
</script>

<style lang="scss">
// As we are using `layouts` plugin we need its styles to be imported
//@use "@layouts/styles/default-layout";
</style>
