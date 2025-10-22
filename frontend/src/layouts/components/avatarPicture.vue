<template>
  <VAvatar
    :color="!hasPicture ? 'secondary' : undefined"
    variant="flat"
    class="ma-1"
  >
    <VImg
      v-if="hasPicture && !imageError"
      :src="pictureUrlWithParams"
      :alt="item.name || 'Profile'"
      cover
      crossorigin="anonymous"
      referrerpolicy="no-referrer"
      @error="handleImageError"
    />
    <VIcon
      v-else
      icon="ri-user-line"
      size="24"
    />
  </VAvatar>
</template>

<script lang="ts">
import {defineComponent} from 'vue';
import { useGoogleProfile } from '@/composables/useGoogleProfile';

interface Item {
  name: string;
  email: string;
  picture: string | null;
}

// Get cached profile data (shared singleton) - this is reactive!
const { profileData, fetchProfile } = useGoogleProfile();

export default defineComponent({
  name: 'AvatarPicture',
  data(){
    return {
      imageError: false,
      loading: true
    }
  },
  computed: {
    // Use the reactive profileData directly
    item(): Item {
      return profileData.value || {
        name: '',
        email: '',
        picture: null
      };
    },
    hasPicture(): boolean {
      return !!this.item.picture && this.item.picture.trim() !== '';
    },
    pictureUrlWithParams(): string | null {
      if (!this.item.picture) return null;
      // Add cache-busting and size parameters to help with Google rate limits
      // Use smaller size to reduce bandwidth (96px instead of default)
      const url = new URL(this.item.picture);
      // Only add params if it's a Googleusercontent URL
      if (url.hostname.includes('googleusercontent.com')) {
        url.searchParams.set('sz', '96'); // Request smaller size
        url.searchParams.set('c', 'cache'); // Request cacheable version
      }
      return url.toString();
    }
  },
  async created(){
    await this.fetchGoogle()
  },
  methods: {
    async fetchGoogle(){
      try {
        // Trigger profile fetch - all components will automatically update
        // when profileData ref changes
        await fetchProfile();

        if (profileData.value) {
          console.log("✅ Avatar auto-updated from reactive profileData");
        } else {
          console.warn("⚠️ No cached profile data available");
        }

        this.$emit('loaded')
      } catch (error) {
        console.error("❌ Error loading profile:", error);
      } finally {
        this.loading = false;
      }
    },
    handleImageError(event: Event) {
      console.warn("⚠️ Failed to load profile picture (429 Too Many Requests is common from Google)");
      console.warn("URL:", this.item.picture);

      // Mark as error to show fallback icon
      this.imageError = true;

      // Note: Google profile pictures often hit rate limits (429 Too Many Requests)
      // The fallback icon will be displayed instead
    }
  }
})
</script>
