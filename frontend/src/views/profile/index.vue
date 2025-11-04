<template>
  <h1>Mi perfil</h1>
  <VCard class="mx-auto pa-6 my-3">
    <VOverlay :model-value="loading" class="d-flex align-center justify-center" opacity="0.85" persistent contained>
      <v-progress-circular indeterminate color="primary" size="64"/>
    </VOverlay>

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
        <VCard variant="outlined" class="mb-4" border="secondary md">
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
            <div class="d-flex align-center mb-3">
              <VIcon icon="ri-account-circle-line" class="mr-3" color="primary"/>
              <div>
                <p class="text-caption text-medium-emphasis mb-1">ID de Usuario</p>
                <p class="text-body-1">{{ userId || 'No disponible' }}</p>
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
      </VCol>
    </VRow>

    <!-- My Profiles Section -->
    <VRow v-if="userId" class="mt-6">
      <VCol cols="12">
        <TableStudentProfiles :user-id="userId" />
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
import API from '@/utils/api';
import TableStudentProfiles from '@/components/tables/users/students/student-profiles/tableStudentProfiles.vue';

interface ProfileData {
  name: string;
  email: string;
  picture: string | null;
}

// Get cached profile data
const { fetchProfile } = useGoogleProfile();

export default defineComponent({
  name: 'ProfileView',
  components: {
    TableStudentProfiles
  },
  data() {
    return {
      profileData: {
        name: '',
        email: '',
        picture: null
      } as ProfileData,
      userId: null as number | null,
      loading: true,
      imageError: false
    };
  },
  async created() {
    await this.loadProfile();
    await this.fetchUserId();
  },
  methods: {
    async fetchUserId() {
      const headers = {
        'API-VERSION': '1',
      };
      try {
        const userData = await API.get(API.USERS_ME, headers);
        console.log("✅ User data fetched:", userData);

        // API.get returns an array, so access first element
        if (userData && userData[0] && userData[0].user_id) {
          this.userId = userData[0].user_id;
          console.log("✅ User ID:", this.userId);
        }
      } catch (error) {
        console.error("❌ Error fetching user ID:", error);
      }
    },
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
