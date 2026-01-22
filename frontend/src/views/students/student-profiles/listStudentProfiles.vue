<script setup lang="ts">
import { ref, onMounted } from 'vue';
import LoadingManager from '@/utils/loadingManager';

const items = [
  { title: 'Estudiantes', disabled: false, to: '/estudiantes' },
  { title: 'Perfiles del estudiante', disabled: true },
];

const loading = ref(true);

onMounted(() => {
  LoadingManager.setTotalComponents(1);
  LoadingManager.reset();
});

const onChildLoaded = () => {
  LoadingManager.onChildLoaded();
  if (LoadingManager.allComponentsLoaded()) {
    loading.value = false;
  }
};
</script>

<template>
  <VCard flat color="transparent">
    <h1>Perfiles del Estudiante</h1>
    <v-breadcrumbs :items="items">
      <template v-slot:divider>
        <v-icon icon="ri-arrow-right-s-line"></v-icon>
      </template>
    </v-breadcrumbs>
    <VOverlay :model-value="loading" class="d-flex align-center justify-center" opacity="0.85" persistent contained>
      <v-progress-circular indeterminate color="primary" size="64"/>
    </VOverlay>
    <TableStudentProfiles @loaded="onChildLoaded"/>
  </VCard>
</template>
