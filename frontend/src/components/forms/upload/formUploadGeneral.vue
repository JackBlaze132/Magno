<template>
  <VCard class="pa-5 ma-5 overflow-auto" max-width="600" max-height="85vh">
    <VCardTitle>Subir {{ label }}</VCardTitle>
    <VDivider/>

    <div v-for="(field, index) in fields" :key="index">

      <div
        v-if="field.type === 'drag-drop'"
        class="drop-zone d-flex flex-column align-center justify-center mt-5"
        :class="{ 'drop-zone--active': isDragging }"
        @click="openFilePicker(index)"
        @dragover.prevent="handleDragOver"
        @dragleave="handleDragLeave"
        @drop.prevent="handleDrop"
      >
        <v-icon size="48">ri-upload-cloud-fill</v-icon>
        <p class="text-subtitle-1 mt-2">Arrastra tu archivo aquí</p>
        <p class="text-caption text-medium-emphasis">o haz clic para seleccionarlo</p>
      </div>

      <v-file-input
        v-if="field.type === 'file-input'"
        ref="fileInput"
        v-model="files"
        :label="field.label"
        prepend-icon="ri-attachment-2"
        variant="outlined"
        class="mt-4"
        :rules="rules"
        accept=".xlsx"
        counter
        show-size
      >
        <!-- Chips personalizados dentro del input -->
        <template v-slot:selection>
          <v-chip
            v-for="(file, index) in files"
            :key="index"
            class="me-2 pa-4"
            size="small"
            :prepend-icon="getFileIcon(file)"
            :color="getFileColor(file)"
            variant="tonal"

          >
            {{ file.name }}
          </v-chip>
        </template>
      </v-file-input>
    </div>

    <VCardItem class="d-flex justify-end">
      <LoadingBtn
        icon="ri-upload-cloud-fill"
        text="Subir"
        :loading="loading"
        color="black"
        @click="submitFile"
      />
    </VCardItem>
  </VCard>
</template>

<script lang="ts">
import { defineComponent } from "vue"
import API from "@/utils/api"
import LoadingBtn from "../loadingBtn.vue"

export default defineComponent({
  name: "FileUploader",
  emits: ["loaded", 'itemUploaded'],

  props: {
    fields: {
      type: Array as () => Array<{ key: string; label: string; type?: string }>,
      default: () => []
    },
    modelValue: {
      type: Array as () => File[],
      default: () => []
    },
    label: {
      type: String,
      default: "tu archivo"
    },
    multiple: {
      type: Boolean,
      default: true
    }
  },

  data() {
    return {
      files: Array.isArray(this.modelValue) ? this.modelValue.slice() : [],
      isDragging: false,
      fileInput: null as any,
      loading: false
    }
  },

  computed: {
    // Reglas de validación
    rules(): ((value: File[]) => true | string)[] {
      return [
        (value: File[]) => {
          if (!value || !value.length) return "Debes seleccionar al menos un archivo"
          const invalid = value.some(
            (f) => (f.name.split(".").pop() || "").toLowerCase() !== "xlsx"
          )
          return !invalid || "Solo se permiten archivos .xlsx"
        }
      ]
    }
  },

  watch: {
    // Sincronizar con el padre → props → data
    modelValue: {
      handler(val: File[]) {
        this.files = Array.isArray(val) ? val.slice() : []
      },
      immediate: true
    },

  },

  created() {
    this.$emit("loaded")
  },

  methods: {
    // Drag & drop
    handleDrop(e: DragEvent) {
      this.isDragging = false
      if (e.dataTransfer?.files && e.dataTransfer.files.length) {
        this.updateFilesFromList(e.dataTransfer.files)
      }
    },
    handleDragOver(e: DragEvent) {
      e.preventDefault()
      this.isDragging = true
    },
    handleDragLeave() {
      this.isDragging = false
    },

    // Abrir file picker manualmente
    openFilePicker(index: number) {
      const refs = this.$refs.fileInput as any[]
      const input = refs?.[index]
      if (input?.$el?.querySelector("input[type=file]")) {
        input.$el.querySelector("input[type=file]").click()
      }
    },

    // Actualizar lista de archivos
    updateFilesFromList(list: FileList | File[]) {
      const arr = Array.from(list as any as File[])
      this.files = this.multiple ? arr : arr.length ? [arr[0]] : []
    },
    removeFile(index: number) {
      this.files.splice(index, 1)
    },

    // Helpers de ícono/color
    getFileIcon(file: File) {
      const ext = (file.name.split(".").pop() || "").toLowerCase()
      return ext === "xlsx" ? "ri-file-excel-2-fill" : "ri-file-warning-fill"
    },
    getFileColor(file: File) {
      const ext = (file.name.split(".").pop() || "").toLowerCase()
      return ext === "xlsx" ? "success" : "error"
    },

    // 🔥 Lógica de subida de archivos
    async submitFile() {
      if (!this.files.length) return

      const file = this.files[0]
      const ext = (file.name.split(".").pop() || "").toLowerCase()
      if (ext !== "xlsx") {
        console.error("Solo se permiten archivos .xlsx")
        return
      }

      const formData = new FormData()
      formData.append("file", file)

      this.loading = true
      const  headers = {
        "API-VERSION": "1",
      }
      try {
        const data = await API.post(
          API.RESEARCH_SEEDBED_STUDENT_PROFILES_UPLOAD_BY_EXCEL + this.$route.params.idSemillero,
          formData, headers
        )

        if (data.error) {
          console.error("Error al subir archivo:", data.error)
        } else {
          console.log("Archivo subido correctamente:", data)
          this.$router.push("detalles")
          this.$emit('itemUploaded')
        }

      } catch (err) {
        console.error("Error al realizar la solicitud", err)
      } finally {
        this.loading = false
      }
    }
  }
})
</script>
