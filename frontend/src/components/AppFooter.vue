<script setup lang="ts">
</script>

<template>
  <VFooter
    class="bg-transparent px-8 py-2 w-100 footer-container"
  >
    <!-- Center: Links and Copyright Group -->
    <div class="footer-center-group d-flex flex-column align-center">
      <div class="footer-links-row d-flex flex-wrap justify-center gap-x-6 gap-y-2 mb-1">
        <a
          href="https://investigaciones.unibague.edu.co/comunidad-investigadora"
          target="_blank"
          class="footer-link d-flex align-center"
        >
          <VIcon icon="ri-group-line" size="18" class="mr-2" />
          <span>Comunidad Investigadora</span>
        </a>
        <a
          href="https://biblioteca.unibague.edu.co/"
          target="_blank"
          class="footer-link d-flex align-center"
        >
          <VIcon icon="ri-book-open-line" size="18" class="mr-2" />
          <span>Biblioteca</span>
        </a>
        <a
          href="https://www.unibague.edu.co/"
          target="_blank"
          class="footer-link d-flex align-center"
        >
          <VIcon icon="ri-bank-line" size="18" class="mr-2" />
          <span>Portal Unibagué</span>
        </a>
      </div>

      <div class="copyright-text text-caption">
        &copy; {{ new Date().getFullYear() }} Magno - Universidad de Ibagué
      </div>
    </div>
  </VFooter>
</template>

<style scoped>
.footer-container {
  position: absolute;
  bottom: 0;
  left: 0;
  display: flex !important;
  align-items: flex-end;
  justify-content: center;
  z-index: 10;
  min-height: 80px;
}

/* Center Content Styling */
.footer-center-group {
  width: auto;
  margin-bottom: 4px;
}

.footer-link {
  color: rgb(var(--v-theme-on-surface));
  text-decoration: none;
  font-size: 0.85rem;
  font-weight: 500;
  white-space: nowrap;
  opacity: 0.9;
  transition: opacity 0.2s;
}

.footer-link:hover {
  opacity: 1;
  text-decoration: underline;
}

.copyright-text {
  color: rgb(var(--v-theme-on-surface));
  opacity: 0.8;
  font-size: 0.75rem !important;
}

/* Space between links */
.gap-x-6 {
  column-gap: 24px;
}

@media (max-width: 960px) {
  .footer-container {
    padding: 16px;
    min-height: auto;
  }

  .footer-links-row {
    gap: 12px;
    margin-bottom: 8px;
  }

  .footer-link {
    font-size: 0.75rem;
  }
}
</style>
