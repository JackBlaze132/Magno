<template>
  <VDialog
    v-model="dialog"
    max-width="600px"
    persistent
    scrollable
  >
    <VCard>
      <VCardTitle class="d-flex align-center certificate-form-title">
        <VIcon class="me-2">ri-verified-badge-line</VIcon>
        Generar Certificado
        <VSpacer />
        <VBtn
          icon="ri-close-line"
          variant="text"
          @click="closeDialog"
        />
      </VCardTitle>

      <VDivider/>

      <VCardText class="pa-6">
        <!-- User ID Display -->
        <VRow class="mb-4">
          <VCol cols="12">
            <VAlert
              type="info"
              variant="tonal"
              class="mb-4"
            >
              <template #prepend>
                <VIcon>ri-account-box-line</VIcon>
              </template>
              <strong>Usuario:</strong> {{ authStore.userName }}
            </VAlert>
          </VCol>
        </VRow>

        <VForm v-if="seedbeds.length > 0 || loadingSeedbeds" @submit.prevent="generateCertificate" ref="certificateForm">
          <VRow>
            <VCol cols="12">
              <VSelect
                v-model="selectedSeedbed"
                :items="seedbeds"
                item-title="name"
                item-value="id"
                label="Seleccionar Semillero"
                :loading="loadingSeedbeds"
                :disabled="loadingSeedbeds"
                variant="outlined"
                prepend-inner-icon="ri-seedling-line"
                :rules="[(v: any) => !!v || 'Seleccione un semillero']"
                required
              >
                <template #no-data>
                  <VListItem>
                    <VListItemTitle>
                      {{ loadingSeedbeds ? 'Cargando semilleros...' : 'No tienes semilleros asignados' }}
                    </VListItemTitle>
                  </VListItem>
                </template>
              </VSelect>
            </VCol>
          </VRow>
        </VForm>

        <VRow v-else>
          <VCol cols="12">
            <VAlert
              type="warning"
              variant="tonal"
              icon="ri-information-line"
            >
              No has sido registrado en ningún semillero.
            </VAlert>
          </VCol>
        </VRow>
      </VCardText>

      <VCardItem class="pe-5 ps-5">
        <VDivider/>
      </VCardItem>

      <VCardItem class="d-flex justify-end">
        <LoadingBtn
          class="me-3"
          text="Cancelar"
          color="error"
          @click="closeDialog"
        />
        <LoadingBtn
          icon="ri-verified-badge-line"
          text="Generar Certificado"
          :loading="generatingCertificate"
          color="primary"
          @click="generateCertificate"
          :disabled="!selectedSeedbed"
        />
      </VCardItem>

    </VCard>
  </VDialog>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import { useAuthStore } from '@/stores/authStore'
import API from '@/utils/api'
import LoadingBtn from '@/components/operators/loadingBtn.vue'
import { useFeedbackToast } from '@/composables/useFeedbackToast'

export default defineComponent({
  name: 'CertificateCreationDialog',
  components: {
    LoadingBtn
  },
  emits: ['certificate-created', 'close'],

  props: {
    modelValue: {
      type: Boolean,
      default: false
    }
  },

  data() {
    return {
      dialog: this.modelValue,
      generatingCertificate: false,
      loadingSeedbeds: false,
      seedbeds: [] as Array<{ id: number; name: string }>,
      selectedSeedbed: null as number | null,
    }
  },

  computed: {
    authStore() {
      return useAuthStore()
    },
    userId() {
      return this.authStore.userId
    }
  },

  watch: {
    modelValue(newVal: boolean) {
      this.dialog = newVal
      if (newVal) {
        this.initializeDialog()
      }
    },
    dialog(newVal: boolean) {
      if (!newVal) {
        this.closeDialog()
      }
    }
  },

  methods: {
    async initializeDialog() {
      console.log('🚀 Initializing certificate dialog...')
      console.log('🔍 Current auth state:', {
        isAuthenticated: this.authStore.isAuthenticated,
        userId: this.userId,
        user: this.authStore.user
      })

      // Ensure user is authenticated and has ID
      if (!this.authStore.isAuthenticated || !this.userId) {
        console.log('⚠️ User not authenticated or no user ID, initializing auth...')
        await this.authStore.initializeAuth()

        console.log('🔍 Auth state after initialization:', {
          isAuthenticated: this.authStore.isAuthenticated,
          userId: this.userId,
          user: this.authStore.user
        })
      }

      // Fetch data if we have user ID
      if (this.userId) {
        console.log('✅ User ID available, fetching data...')
        await this.fetchUserSeedbeds()
      } else {
        console.error('❌ Still no user ID after auth initialization')
        useFeedbackToast().showError({ message: "No se pudo obtener la información del usuario" })
      }
    },

    async fetchUserSeedbeds() {
      if (!this.userId) {
        console.warn('❌ User ID not available for fetching seedbeds')
        useFeedbackToast().showError({ message: "ID de usuario no disponible" })
        return
      }

      this.loadingSeedbeds = true
      try {
        const headers = {
          'API-VERSION': '1',
        }
        const endpoint = `${API.RESEARCH_SEEDBEDS_BY_USER_ID}${this.userId}`
        console.log('🔍 Fetching seedbeds from:', endpoint)

        const response = await API.get(endpoint, headers)
        console.log('📡 Seedbeds API response:', response)

        if (response && Array.isArray(response) && response.length > 0) {
          this.seedbeds = response.map((seedbed: any) => {
            console.log('🌱 Processing seedbed:', seedbed)
            return {
              id: seedbed.id || seedbed.seedbed_id,
              name: seedbed.name || seedbed.seedbed_name || seedbed.research_seedbed_name || `Semillero ${seedbed.id || seedbed.seedbed_id}`
            }
          })
          console.log('✅ Seedbeds loaded successfully:', this.seedbeds)

          if (this.seedbeds.length === 0) {
            console.log('⚠️ No valid seedbeds found for user')
          }
        } else {
          this.seedbeds = []
          console.log('⚠️ No seedbeds found in response or empty array')
        }
      } catch (error) {
        console.error('❌ Error fetching user seedbeds:', error)
        this.seedbeds = []
      } finally {
        this.loadingSeedbeds = false
      }
    },

    async generateCertificate() {
      if (!this.selectedSeedbed) {
        useFeedbackToast().showError({ message: 'Por favor selecciona un semillero' })
        return
      }

      if (!this.userId) {
        useFeedbackToast().showError({ message: 'ID de usuario no disponible' })
        return
      }

      this.generatingCertificate = true
      try {
        // Build the JSON body for the certificate generation
        const requestBody = {
          user_id: this.userId,
          research_seedbed_id: this.selectedSeedbed
        }
        const headers = {
          'API-VERSION': '1',
        }

        console.log('🚀 Generating certificate with data:', requestBody)

        const response = await API.post(API.GENERATE_CERTIFICATES, requestBody, headers )

        if (response) {
          console.log('Certificate generated response:', response)

          // Handle the response if it's the raw Fetch Response object (binary PDF data)
          if (response instanceof Response) {
            const blob = await response.blob()
            const url = window.URL.createObjectURL(blob)

            // Open the PDF in a new tab instead of downloading
            window.open(url, '_blank')

            useFeedbackToast().showSuccess('Certificado generado exitosamente')
          } else if (response.downloadUrl || response.fileData) {
            // Handle case where response might be JSON with a URL
            window.open(response.downloadUrl || response.fileData, '_blank')
            useFeedbackToast().showSuccess('Certificado generado exitosamente')
          } else {
            // If it's something else but not null, we still emit it
            useFeedbackToast().showSuccess('Certificado generado exitosamente')
          }

          this.$emit('certificate-created', response)
          this.closeDialog()
        } else {
          useFeedbackToast().showError({ message: 'No se pudo generar el certificado' })
        }
      } catch (error) {
        console.error('❌ Error generating certificate:', error)
        useFeedbackToast().showError({ message: 'Error al generar el certificado' })
      } finally {
        this.generatingCertificate = false
      }
    },

    closeDialog() {
      this.dialog = false
      this.selectedSeedbed = null
      this.$emit('close')
    }
  }
})
</script>

