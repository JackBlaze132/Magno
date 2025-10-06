<template>
  <VCard class="pa-5 ma-5 overflow-auto" max-width="600" max-height="85vh">
    <VCardTitle>Subir {{ label }}</VCardTitle>
    <VDivider/>

    <div v-for="(field, index) in fields" :key="index">

      <!-- Drag and drop zone -->
      <DragDropUpload
        v-if="field.type === 'drag-drop'"
        :accept="field.accept"
        :multiple="(field.multiple !== false) && multiple"
        :disabled="(field.disabled || false) || loading"
        @files-selected="handleFilesSelected"
      />

      <!-- Traditional file input -->
      <FileInputUpload
        v-else-if="field.type === 'file-input' || field.type === 'file'"
        v-model="files"
        :label="field.label"
        :accept="field.accept"
        :multiple="(field.multiple !== false) && multiple"
        :disabled="(field.disabled || false) || loading"
        :rules="getFieldRules(field)"
        :max-size="field.maxSize"
        @files-selected="handleFilesSelected"
      />

      <!-- Text input for file paths or URLs -->
      <v-text-field
        v-else-if="field.type === 'text'"
        v-model="formValues[field.key]"
        :label="field.label"
        prepend-icon="ri-link"
        variant="outlined"
        class="mt-4"
        :disabled="(field.disabled || false) || loading"
      />

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


interface UploadField {
  key: string;
  label: string;
  type?: string;
  accept?: string;
  rules?: Array<(value: File[]) => true | string>;
  multiple?: boolean;
  required?: boolean;
  disabled?: boolean;
  maxSize?: number;
}

export default defineComponent({
  name: "FileUploader",
  emits: ["loaded", 'itemUploaded'],

  props: {
    type: {
      type: String,
      required: true
    },
    fields: {
      type: Array as () => UploadField[],
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
    },
    index: {
      type: [String, Number],
      default: null
    },
    additionalData: {
      type: Object,
      default: () => ({})
    }
  },

  data() {
    return {
      files: Array.isArray(this.modelValue) ? this.modelValue.slice() : [],
      loading: false,
      formValues: {} as Record<string, any>
    }
  },

  computed: {
    // Dynamic validation rules based on field configuration
    rules(): ((value: File[]) => true | string)[] {
      const field = this.fields[0]; // Assume single field for now
      if (field?.rules) {
        return field.rules;
      }

      // Default rules
      const defaultRules: ((value: File[]) => true | string)[] = [
        (value: File[]) => {
          if (field?.required !== false && (!value || !value.length)) {
            return "Debes seleccionar al menos un archivo";
          }
          return true;
        }
      ];

      // Add file type validation if accept is specified
      if (field?.accept) {
        const acceptedTypes = field.accept.split(',').map((type: string) => type.trim().toLowerCase());
        defaultRules.push((value: File[]) => {
          if (!value || !value.length) return true;
          const invalid = value.some((f) => {
            const ext = (f.name.split(".").pop() || "").toLowerCase();
            return !acceptedTypes.some((acceptType: string) =>
              acceptType.includes(ext) || acceptType === `*.${ext}` || acceptType === `.${ext}`
            );
          });
          return !invalid || `Solo se permiten archivos: ${field.accept}`;
        });
      }

      return defaultRules;
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
    // Handle files selected from child components
    handleFilesSelected(files: File[]) {
      this.files = files
    },

    // Get validation rules for a specific field
    getFieldRules(field: UploadField): ((value: File[]) => true | string)[] {
      if (field.rules) {
        return field.rules
      }
      return this.rules
    },

    // Get the appropriate API endpoint based on type
    getApiEndpoint() {
      console.log('Getting endpoint for type:', this.type);
      const baseEndpoints = {
        'seedbed_member': API.RESEARCH_SEEDBED_STUDENT_PROFILES_UPLOAD_BY_EXCEL,
      };

      const endpoint = baseEndpoints[this.type as keyof typeof baseEndpoints];
      if (!endpoint) {
        console.error('Unsupported upload type:', this.type);
        throw new Error(`Unsupported upload type: ${this.type}`);
      }

      // Add index if provided (for specific resource uploads)
      const fullEndpoint = this.index ? `${endpoint}${this.index}` : endpoint;
      console.log('Using endpoint:', fullEndpoint);
      return fullEndpoint;
    },

    // Validate files before upload
    validateFiles(): boolean {
      if (!this.files.length) {
        console.error("No files selected");
        return false;
      }

      // Run validation rules
      const validationResults = this.rules.map(rule => rule(this.files));
      const errors = validationResults.filter(result => result !== true);

      if (errors.length > 0) {
        console.error("Validation errors:", errors);
        return false;
      }

      return true;
    },

    // 🔥 Flexible file upload logic
    async submitFile() {
      console.log('Files to upload:', this.files);
      console.log('Files length:', this.files.length);

      if (!this.validateFiles()) return;

      const formData = new FormData();

      // Add files to form data
      // Always use 'file' as the key - backend expects this
      if (this.multiple && this.files.length > 1) {
        // For multiple files, append each with the same key 'file'
        this.files.forEach((file, index) => {
          console.log(`Appending file ${index}:`, file.name);
          formData.append("file", file);
        });
      } else if (this.files.length > 0) {
        // For single file
        console.log('Appending single file:', this.files[0].name);
        formData.append("file", this.files[0]);
      }

      console.log('FormData created with', this.files.length, 'files');

      // Add additional data if provided
      Object.entries(this.additionalData).forEach(([key, value]) => {
        formData.append(key, String(value));
      });

      this.loading = true;
      const headers = {
        "API-VERSION": "1",
      };

      try {
        const endpoint = this.getApiEndpoint();
        const data = await API.post(endpoint, formData, headers);

        if (data.error) {
          console.error("Error al subir archivo:", data.error);
        } else {
          console.log("Archivo subido correctamente:", data);
          this.$emit('itemUploaded', data);

          // Optional navigation - can be handled by parent component
          if (this.$route.name && this.type === 'seedbed_students') {
            this.$router.push("detalles");
          }
        }

      } catch (err) {
        console.error("Error al realizar la solicitud", err);
      } finally {
        this.loading = false;
      }
    }
  }
})
</script>
