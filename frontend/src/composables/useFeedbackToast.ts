import { ref } from 'vue';
/**
 * Interface representing the structure of an error response.
 */
interface ErrorResponse {
  code?: string; // Optional error code
  message?: string; // Optional error message
  details?: string[]; // Optional array of error details
  timestamp?: string; // Optional timestamp of the error
  exceptionClassName?: string; // Optional name of the exception class
}

/**
 * A reference to the FeedbackToast component instance.
 * This is used to display error and success messages.
 */
const errorToastInstance = ref<any>(null);

/**
 * Composable function to manage feedback toast notifications.
 * Provides methods to show error and success messages, and to register the toast instance.
 *
 * @returns {Object} An object containing methods to show error, show success, and register the toast instance.
 */
export function useFeedbackToast() {
  /**
   * Displays an error message using the FeedbackToast component.
   *
   * @param {ErrorResponse | undefined} errorData - The error data to display. If undefined, no error is shown.
   */
  const showError = (errorData: ErrorResponse | undefined) => {
    if (!errorToastInstance.value) {
      console.error('FeedbackToast component not initialized');
      return;
    }
    errorToastInstance.value.showError(errorData);
  };

  /**
   * Displays a success message using the FeedbackToast component.
   *
   * @param {string} [message='Operation completed successfully'] - The success message to display.
   */
  const showSuccess = (message: string = 'Operation completed successfully') => {
    if (!errorToastInstance.value) {
      console.error('FeedbackToast component not initialized');
      return;
    }
    errorToastInstance.value.showSuccess(message);
  };

  /**
   * Registers the FeedbackToast component instance.
   *
   * @param {any} instance - The instance of the FeedbackToast component to register.
   */
  const registerToastInstance = (instance: any) => {
    errorToastInstance.value = instance;
  };

  return {
    showError,
    showSuccess,
    registerToastInstance,
  };
}
