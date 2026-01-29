<template>
  <v-file-input
    v-model="localFiles"
    :label="label"
    prepend-icon="ri-attachment-2"
    variant="outlined"
    class="mt-4"
    :rules="validationRules"
    :accept="accept || '*'"
    :multiple="multiple"
    :disabled="disabled"
    counter
    show-size
    clearable
    @update:modelValue="handleFileChange"
  >
    <!-- Custom file chips with enhanced styling -->
    <template v-slot:selection>
      <v-chip
        v-for="(file, chipIndex) in localFiles"
        :key="chipIndex"
        class="me-2 pa-4"
        size="small"
        :prepend-icon="getFileIcon(file)"
        :color="getFileColor(file)"
        variant="tonal"

        @click:close="removeFile(chipIndex)"
      >
        {{ file.name }}
        <span class="text-caption ml-1">({{ formatFileSize(file.size) }})</span>
      </v-chip>
    </template>
  </v-file-input>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import type { PropType } from 'vue'

export default defineComponent({
  name: 'FileInputUpload',
  emits: ['filesSelected', 'update:modelValue'],

  props: {
    modelValue: {
      type: Array as PropType<File[]>,
      default: () => []
    },
    label: {
      type: String,
      default: 'Seleccionar archivos'
    },
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
    },
    rules: {
      type: Array as PropType<((value: File[]) => true | string)[]>,
      default: () => []
    },
    maxSize: {
      type: Number,
      default: null // in bytes
    }
  },

  data() {
    return {
      localFiles: [...this.modelValue] as File[]
    }
  },

  computed: {
    validationRules(): ((value: File[]) => true | string)[] {
      const rules = [...this.rules]

      // Add max size validation if specified
      if (this.maxSize) {
        rules.push((files: File[]) => {
          const oversized = files.find(file => file.size > this.maxSize!)
          return !oversized || `Archivo "${oversized.name}" excede el tamaño máximo de ${this.formatFileSize(this.maxSize!)}`
        })
      }

      return rules
    }
  },

  watch: {
    modelValue: {
      handler(newFiles: File[]) {
        this.localFiles = [...newFiles]
      },
      deep: true
    }
  },

  methods: {
    handleFileChange(files: File | File[] | null) {
      if (files === null) {
        this.localFiles = []
      } else if (Array.isArray(files)) {
        this.localFiles = files
      } else {
        this.localFiles = [files]
      }
      this.$emit('update:modelValue', this.localFiles)
      this.$emit('filesSelected', this.localFiles)
    },

    removeFile(index: number) {
      this.localFiles.splice(index, 1)
      this.$emit('update:modelValue', this.localFiles)
      this.$emit('filesSelected', this.localFiles)
    },

    // File utility methods
    getFileIcon(file: File): string {
      const ext = (file.name.split('.').pop() || '').toLowerCase()
      const iconMap: Record<string, string> = {
        'xlsx': 'ri-file-excel-2-fill',
        'xls': 'ri-file-excel-2-fill',
        'csv': 'ri-file-text-fill',
        'pdf': 'ri-file-pdf-2-fill',
        'doc': 'ri-file-word-2-fill',
        'docx': 'ri-file-word-2-fill',
        'jpg': 'ri-image-fill',
        'jpeg': 'ri-image-fill',
        'png': 'ri-image-fill',
        'gif': 'ri-image-fill',
        'svg': 'ri-image-fill',
        'zip': 'ri-file-zip-fill',
        'rar': 'ri-file-zip-fill',
        '7z': 'ri-file-zip-fill',
        'mp4': 'ri-video-fill',
        'avi': 'ri-video-fill',
        'mov': 'ri-video-fill',
        'mp3': 'ri-music-fill',
        'wav': 'ri-music-fill',
        'txt': 'ri-file-text-fill',
        'json': 'ri-file-code-fill',
        'js': 'ri-file-code-fill',
        'ts': 'ri-file-code-fill',
        'vue': 'ri-file-code-fill'
      }
      return iconMap[ext] || 'ri-file-fill'
    },

    getFileColor(file: File): string {
      const ext = (file.name.split('.').pop() || '').toLowerCase()

      // Validate against accept property if specified
      if (this.accept && this.accept !== '*') {
        const acceptedTypes = this.accept.split(',').map(type => type.trim().toLowerCase())
        const isAccepted = acceptedTypes.some(acceptType =>
          acceptType.includes(ext) ||
          acceptType === `*.${ext}` ||
          acceptType === `.${ext}` ||
          acceptType === ext
        )
        if (!isAccepted) return 'error'
      }

      const colorMap: Record<string, string> = {
        'xlsx': 'success',
        'xls': 'success',
        'csv': 'info',
        'pdf': 'error',
        'doc': 'primary',
        'docx': 'primary',
        'jpg': 'secondary',
        'jpeg': 'secondary',
        'png': 'secondary',
        'gif': 'secondary',
        'svg': 'secondary',
        'zip': 'warning',
        'rar': 'warning',
        '7z': 'warning',
        'mp4': 'purple',
        'avi': 'purple',
        'mov': 'purple',
        'mp3': 'orange',
        'wav': 'orange'
      }
      return colorMap[ext] || 'default'
    },

    formatFileSize(bytes: number): string {
      if (bytes === 0) return '0 Bytes'
      const k = 1024
      const sizes = ['Bytes', 'KB', 'MB', 'GB']
      const i = Math.floor(Math.log(bytes) / Math.log(k))
      return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i]
    }
  }
})
</script>

