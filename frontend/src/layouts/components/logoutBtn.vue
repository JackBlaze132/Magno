<template v-slot:append>
  <div class="pa-2">
    <v-btn
      color="black"
      block
      prepend-icon="ri-logout-box-line"
      @click="handleGoogleLogout"
    >
      Cerrar sesión
    </v-btn>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import API from '@/utils/api';

export default defineComponent({
  name: 'LogoutBtn',
  methods: {
    handleGoogleLogout() {
      API.logout();
    }
  }
});
</script>
