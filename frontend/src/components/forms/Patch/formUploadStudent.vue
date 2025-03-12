<template>
  <div class="auth-wrapper d-flex align-md-end align-sm-center flex-md-column justify-center pa-4">
    <VCol cols="12">
      <VForm ref="form" @submit.prevent="submitFile">
        <VFileInput
          v-model="file"
          label="Archivo de excel"
          type="file"
          accept=".xlsx"
          :rules="rules"
        >
          <template v-slot:selection="{ fileNames }">
            <template v-for="(fileName) in fileNames" :key="fileName">
              <VChip
                class="me-2 pa-4"
                size="small"
                :prepend-icon="icon"
              >
                {{ fileName }}
              </VChip>
            </template>
          </template>
        </VFileInput>
        <span class="d-flex justify-center">
          <LoadingBtn icon="ri-upload-cloud-2-fill" text="Cargar" :loading="loading"></LoadingBtn>
        </span>
      </VForm>
    </VCol>
  </div>
</template>

<script lang="ts">
import LoadingBtn from '../loadingBtn.vue';
import API from '@/utils/api';

export default {
  data: () => ({
    file: [] as any,
    loading: false,
    isFileValid: true, // Nueva propiedad para manejar la validación
  }),
  computed: {
    icon() {
      return this.isFileValid ? 'ri-file-excel-2-fill' : null;
    },
    rules() {
      // Retornamos la función de validación para usar en el VFileInput
      return [
        (value: File[]) => this.validateFile(value)
      ];
    }
  },
  methods: {
    validateFile(value: File[]): boolean | string {
      if (value.length === 0) {
        this.isFileValid = false;
        return 'El archivo es requerido';
      }
      const fileName = value[0].name;
      const fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();

      this.isFileValid = fileExtension === 'xlsx';
      return this.isFileValid || 'Solo se permiten archivos .xlsx';
    },
    submitFile(): void {
      if (this.file.length === 0) return;
      const file = this.file[0];
      const fileName = file.name;
      const fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();

      if (fileExtension !== 'xlsx') {
        console.error('Solo se permiten archivos .xlsx');
      } else {
        this.prepareFile();
      }
    },
    prepareFile(): void {
      const formData = new FormData();
      formData.append('file', this.file[0]);

      this.loading = true;
      fetch('/api/' + API.POST_STUDENT_PROFILE_BY_EXCEL + this.$route.params.idSemillero, {
        method: 'POST',
        body: formData,
      })
        .then(response => response.json())
        .then(data => {
          if (data.error) {
            console.error('Error al realizar la solicitud', data.error);
            this.loading = false;
          } else {
            console.log(data);
            this.$router.push('detalles');
          }
        })
        .catch(error => console.error('Error al realizar la solicitud', error));
    }
  }
}
</script>
