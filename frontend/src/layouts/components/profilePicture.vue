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
              @click="goToProfile"
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

<script setup lang="ts">
import { computed, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { useAuthStore } from '@/stores/authStore';
import AvatarPicture from './avatarPicture.vue';

const authStore = useAuthStore();
const router = useRouter();

const emit = defineEmits<{
  loaded: []
}>();

const item = computed(() => ({
  name: authStore.userName,
  email: authStore.userEmail,
  picture: authStore.userPicture
}));

const onAvatarLoaded = () => {
  // Avatar loaded successfully
};

const initializeAuth = async () => {
  if (!authStore.isAuthenticated || !authStore.user) {
    await authStore.initializeAuth();
  }
  emit('loaded');
};

const goToProfile = () => {
  router.push('/perfil');
};

onMounted(() => {
  initializeAuth();
});
</script>

<script lang="ts">
import { defineComponent } from 'vue';
export default defineComponent({
  name: 'ProfilePicture'
});
</script>

