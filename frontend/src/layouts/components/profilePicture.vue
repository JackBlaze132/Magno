<template>
  <div class="profile-picture-container">
    <v-menu min-width="200px">
      <template v-slot:activator="{ props }">
        <IconBtn v-bind="props">
          <AvatarPicture @loaded="onAvatarLoaded" />
        </IconBtn>
      </template>
      <VCard>
        <VCardText>
          <div class="mx-auto text-center">
            <AvatarPicture />
            <h3>{{ item.name }}</h3>
            <p class="text-caption mt-1">
              {{ item.email }}
            </p>
            <VDivider class="my-3"></VDivider>
            <VBtn
              variant="text"
              rounded
              prepend-icon="ri-user-smile-line"
            >
              Tu perfil
            </VBtn>
            <VDivider class="my-3"></VDivider>
            <LogoutBtn/>
          </div>
        </VCardText>
      </VCard>
    </v-menu>
  </div>
</template>

<script lang="ts">
import {defineComponent} from 'vue';
import AvatarPicture from './avatarPicture.vue';
import { useGoogleProfile } from '@/composables/useGoogleProfile';
//import LogoutBtn from "./logoutBtn.vue";

interface Item {
  name: string;
  email: string;
  picture: string | null;
}

// Get cached profile data (singleton - shared across all instances)
const { fetchProfile } = useGoogleProfile();

export default defineComponent({
  name: "ProfilePicture",
  components: {
    //LogoutBtn,
    AvatarPicture
  },
  data(){
    return {
      item: {
        name: 'Loading...',
        email: '',
        picture: null
      } as Item
    }
  },
  async created() {
    await this.fetchGoogle();
  },
  methods: {
    onAvatarLoaded() {
      // Avatar loaded successfully
    },
    async fetchGoogle(){
      // Use cached data if available
      const profile = await fetchProfile();
      
      if (profile) {
        this.item = profile;
        console.log("✅ Profile loaded (from cache or fresh):", this.item);
      } else {
        console.warn("⚠️ No profile data available");
        this.item = {
          name: 'User',
          email: 'Not available',
          picture: null
        };
      }
      
      this.$emit('loaded')
    }
  }
})
</script>

