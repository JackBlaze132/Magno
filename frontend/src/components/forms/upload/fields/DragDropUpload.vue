<template>
  <div
    class="drop-zone d-flex flex-column align-center justify-center mt-5"
    :class="{ 'drop-zone--active': isDragging }"
    @click="triggerFileInput"
    @dragover.prevent="handleDragOver"
    @dragleave="handleDragLeave"
    @drop.prevent="handleDrop"
  >
    <v-icon size="48">ri-upload-cloud-fill</v-icon>
    <p class="text-subtitle-1 mt-2">
      Arrastra tu archivo
      <span v-if="accept" class="px-1" style="background-color:rgb(var(--v-theme-grey-300))">
        {{ accept }}
      </span>
      aquí
    </p>
    <p class="text-caption text-medium-emphasis">o haz clic para seleccionarlo</p>

    <!-- Hidden file input -->
    <input
      ref="hiddenFileInput"
      type="file"
      :accept="accept"
      :multiple="multiple"
      style="display: none"
      @change="handleFileInput"
    />
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue'

export default defineComponent({
  name: 'DragDropUpload',
  emits: ['filesSelected'],

  props: {
    accept: {
      type: String,
      default: '*'
    },
    multiple: {
      type: Boolean,
      default: true
    },
    disabled: {
      type: Boolean,
      default: false
    }
  },

  data() {
    return {
      isDragging: false
    }
  },

  methods: {
    handleDragOver(e: DragEvent) {
      if (this.disabled) return
      e.preventDefault()
      this.isDragging = true
    },

    handleDragLeave() {
      if (this.disabled) return
      this.isDragging = false
    },

    handleDrop(e: DragEvent) {
      if (this.disabled) return
      e.preventDefault()
      this.isDragging = false

      if (e.dataTransfer?.files && e.dataTransfer.files.length) {
        const files = Array.from(e.dataTransfer.files)
        this.emitFiles(files)
      }
    },

    triggerFileInput() {
      if (this.disabled) return
      const input = this.$refs.hiddenFileInput as HTMLInputElement
      input?.click()
    },

    handleFileInput(e: Event) {
      const target = e.target as HTMLInputElement
      if (target.files && target.files.length) {
        const files = Array.from(target.files)
        this.emitFiles(files)
      }
    },

    emitFiles(files: File[]) {
      const selectedFiles = this.multiple ? files : files.slice(0, 1)
      this.$emit('filesSelected', selectedFiles)
    }
  }
})
</script>
