<template>
  <div>
    <!-- Error Toast -->
    <transition name="slide-fade">
      <VAlert
        v-if="visible && type === 'error'"
        type="error"
        closable
        class="error-toast"
        border="start"
        @update:model-value="handleClose"
      >
        <div v-if="errorMessage" class="text-h6">
          {{ errorMessage }}
        </div>

        <div v-if="errorDetails.length" class="mt-2 text-body-2">
          <ul>
            <li v-for="(detail, index) in errorDetails" :key="index">
              {{ detail }}
            </li>
          </ul>
        </div>
        <hr class="alert-divider" />
        <div v-if="errorCode" class="font-weight-light text-code text-end">
          [{{ errorCode }}]
        </div>



      </VAlert>
    </transition>

    <!-- Success Toast -->
    <transition name="slide-fade">
      <VAlert
        v-if="visible && type === 'success'"
        type="success"
        closable
        class="error-toast"
        border="start"
        @update:model-value="handleClose"
      >
        <div>{{ successMessage }}</div>
      </VAlert>
    </transition>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import { useFeedbackToast } from '@/utils/useFeedbackToast'

interface ErrorResponse {
  code?: string
  message?: string
  details?: string[]
  timestamp?: string
  exceptionClassName?: string
}

export default defineComponent({
  name: 'ErrorToast',
  setup() {
    const { registerToastInstance } = useFeedbackToast()
    return { registerToastInstance }
  },
  data() {
    return {
      visible: false,
      type: 'error' as 'error' | 'success',
      errorCode: '',
      errorMessage: '',
      errorDetails: [] as string[],
      successMessage: '',
      timeout: null as any
    }
  },
  mounted() {
    this.registerToastInstance(this)
  },
  beforeUnmount() {
    if (this.timeout) {
      clearTimeout(this.timeout)
    }
  },
  methods: {
    showError(errorData: ErrorResponse | undefined) {
      if (!errorData) {
        return
      }

      this.type = 'error'
      this.errorCode = errorData.code || 'Unknown Error'
      this.errorMessage = errorData.message || 'An unknown error occurred'
      this.errorDetails = errorData.details || []
      this.visible = true

      // Auto-dismiss after 5 seconds
      if (this.timeout) {
        clearTimeout(this.timeout)
      }
      this.timeout = setTimeout(() => {
        this.visible = false
      }, 5000)
    },
    showSuccess(message: string = 'Operation completed successfully') {
      this.type = 'success'
      this.successMessage = message
      this.visible = true

      // Auto-dismiss after 4 seconds
      if (this.timeout) {
        clearTimeout(this.timeout)
      }
      this.timeout = setTimeout(() => {
        this.visible = false
      }, 4000)
    },
    handleClose() {
      this.visible = false
      if (this.timeout) {
        clearTimeout(this.timeout)
      }
    }
  }
})
</script>


