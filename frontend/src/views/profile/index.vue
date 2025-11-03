<template>
  <VCard class="mx-auto pa-6">
    <VOverlay :model-value="loading" class="d-flex align-center justify-center" opacity="0.85" persistent contained>
      <v-progress-circular indeterminate color="primary" size="64"/>
    </VOverlay>

    <VCardTitle class="text-h4 mb-4">Mi Perfil</VCardTitle>
    <VDivider class="mb-6"/>

    <VRow>
      <!-- Avatar and Basic Info Section -->
      <VCol cols="12" md="4" class="text-center">
        <VAvatar
          :color="!profileData.picture ? 'secondary' : undefined"
          size="150"
          class="mb-4"
        >
          <VImg
            v-if="profileData.picture"
            :src="profileData.picture"
            :alt="profileData.name"
            cover
            crossorigin="anonymous"
            referrerpolicy="no-referrer"
            @error="handleImageError"
          />
          <VIcon
            v-else
            icon="ri-user-line"
            size="80"
          />
        </VAvatar>
        <h2 class="text-h5 mb-2">{{ profileData.name || 'Usuario' }}</h2>
        <p class="text-body-2 text-medium-emphasis">{{ profileData.email || 'No disponible' }}</p>
      </VCol>

      <!-- Profile Details Section -->
      <VCol cols="12" md="8">
        <VCard variant="outlined" class="mb-4">
          <VCardText>
            <div class="d-flex align-center mb-3">
              <VIcon icon="ri-user-line" class="mr-3" color="primary"/>
              <div>
                <p class="text-caption text-medium-emphasis mb-1">Nombre completo</p>
                <p class="text-body-1">{{ profileData.name || 'No disponible' }}</p>
              </div>
            </div>
            <VDivider class="my-3"/>
            <div class="d-flex align-center mb-3">
              <VIcon icon="ri-mail-line" class="mr-3" color="primary"/>
              <div>
                <p class="text-caption text-medium-emphasis mb-1">Correo electrónico</p>
                <p class="text-body-1">{{ profileData.email || 'No disponible' }}</p>
              </div>
            </div>
            <VDivider class="my-3"/>
            <div class="d-flex align-center">
              <VIcon icon="ri-shield-check-line" class="mr-3" color="primary"/>
              <div>
                <p class="text-caption text-medium-emphasis mb-1">Estado de la cuenta</p>
                <VChip color="success" size="small">Activa</VChip>
              </div>
            </div>
          </VCardText>
        </VCard>

        <!-- Additional Info Card -->
        <VCard variant="outlined">
          <VCardText>
            <h3 class="text-h6 mb-3">Información adicional</h3>
            <div class="d-flex align-center mb-3">
              <VIcon icon="ri-login-circle-line" class="mr-3" color="primary"/>
              <div>
                <p class="text-caption text-medium-emphasis mb-1">Método de autenticación</p>
                <p class="text-body-1">Google OAuth</p>
              </div>
            </div>
          </VCardText>
        </VCard>
      </VCol>
    </VRow>

    <!-- Action Buttons -->
    <VCardActions class="mt-6 justify-end">
      <VBtn
        color="primary"
        variant="tonal"
        prepend-icon="ri-arrow-left-line"
        @click="goBack"
      >
        Volver
      </VBtn>
    </VCardActions>
  </VCard>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import { useGoogleProfile } from '@/composables/useGoogleProfile';

interface ProfileData {
  name: string;
  email: string;
  picture: string | null;
}

// Get cached profile data
const { profileData: cachedProfile, fetchProfile } = useGoogleProfile();

export default defineComponent({
  name: 'ProfileView',
  data() {
    return {
      profileData: {
        name: '',
        email: '',
        picture: null
      } as ProfileData,
      loading: true,
      imageError: false
    };
  },
  async created() {
    await this.loadProfile();
  },
  methods: {
    async loadProfile() {
      this.loading = true;
      try {
        // Fetch profile data using cached composable
        const profile = await fetchProfile();

        if (profile) {
          this.profileData = profile;
          console.log("✅ Profile view loaded:", this.profileData);
        } else {
          console.warn("⚠️ No profile data available");
          this.profileData = {
            name: 'Usuario',
            email: 'No disponible',
            picture: null
          };
        }
      } catch (error) {
        console.error("❌ Error loading profile:", error);
        this.profileData = {
          name: 'Usuario',
          email: 'No disponible',
          picture: null
        };
      } finally {
        this.loading = false;
      }
    },
    handleImageError() {
      console.warn("⚠️ Failed to load profile picture");
      this.imageError = true;
    },
    goBack() {
      this.$router.back();
    }
  }
});
</script>

<style scoped>
.text-medium-emphasis {
  opacity: 0.7;
}
</style>
