import { ref } from 'vue'

interface ErrorResponse {
  code?: string
  message?: string
  details?: string[]
  timestamp?: string
  exceptionClassName?: string
}

const errorToastInstance = ref<any>(null)

export function useFeedbackToast() {
  const showError = (errorData: ErrorResponse | undefined) => {
    if (!errorToastInstance.value) {
      console.error('FeedbackToast component not initialized')
      return
    }
    errorToastInstance.value.showError(errorData)
  }

  const showSuccess = (message: string = 'Operation completed successfully') => {
    if (!errorToastInstance.value) {
      console.error('FeedbackToast component not initialized')
      return
    }
    errorToastInstance.value.showSuccess(message)
  }

  const registerToastInstance = (instance: any) => {
    errorToastInstance.value = instance
  }

  return {
    showError,
    showSuccess,
    registerToastInstance
  }
}
