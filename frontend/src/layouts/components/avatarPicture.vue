<template>
  <VAvatar
    :color="!hasPicture ? 'secondary' : undefined"
    variant="flat"
    class="ma-1 avatar-rounded"
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

<script setup lang="ts">
import { computed, ref, onMounted } from 'vue';
import { useAuthStore } from '@/stores/authStore';

const authStore = useAuthStore();
const imageError = ref(false);
const loading = ref(true);

const emit = defineEmits<{
  loaded: []
}>();

const item = computed(() => ({
  name: authStore.userName,
  email: authStore.userEmail,
  picture: authStore.userPicture
}));

const hasPicture = computed(() => {
  return !!item.value.picture && item.value.picture.trim() !== '';
});

const pictureUrlWithParams = computed(() => {
  if (!item.value.picture) return '';
  try {
    const url = new URL(item.value.picture);
    if (url.hostname.includes('googleusercontent.com')) {
      url.searchParams.set('sz', '96');
      url.searchParams.set('c', 'cache');
    }
    return url.toString();
  } catch {
    return item.value.picture;
  }
});

const initializeAuth = async () => {
  try {
    if (!authStore.isAuthenticated || !authStore.user) {
      await authStore.initializeAuth();
    }
    console.log("✅ Avatar using authStore:", item.value);
    emit('loaded');
  } catch (error) {
    console.error("❌ Error loading profile:", error);
  } finally {
    loading.value = false;
  }
};

const handleImageError = () => {
  console.warn("⚠️ Failed to load profile picture");
  imageError.value = true;
};

onMounted(() => {
  initializeAuth();
});
</script>

<script lang="ts">
import { defineComponent } from 'vue';
export default defineComponent({
  name: 'AvatarPicture'
});
</script>

<style scoped>
.avatar-rounded {
  border-radius: 50% !important;
  overflow: hidden;
}

.avatar-rounded .v-img {
  border-radius: 50% !important;
}
</style>
