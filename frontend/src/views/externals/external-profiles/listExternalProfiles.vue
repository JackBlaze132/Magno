<script setup lang="ts">
import { ref, onMounted } from 'vue';
import LoadingManager from '@/utils/loadingManager';

const items = [
  { title: 'Aliados Externos', disabled: false, to: '/aliados-externos' },
  { title: 'Perfiles del aliado', disabled: true },
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
