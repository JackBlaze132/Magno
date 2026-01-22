import { describe, it, expect } from 'vitest'
import { mount } from '@vue/test-utils'
import feedbackToast from '../feedbackToast.vue'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'

const vuetify = createVuetify({
  components,
  directives,
})

describe('feedbackToast.vue', () => {
  it('renders success message when type is success', async () => {
    const wrapper = mount(feedbackToast, {
      global: {
        plugins: [vuetify],
      },
      data() {
        return {
          visible: true,
          type: 'success',
          successMessage: 'Operation successful'
        }
      }
    })

    expect(wrapper.text()).toContain('Operation successful')
  })

  it('renders error message when type is error', async () => {
    const wrapper = mount(feedbackToast, {
      global: {
        plugins: [vuetify],
      },
      data() {
        return {
          visible: true,
          type: 'error',
          errorMessage: 'An error occurred'
        }
      }
    })

    expect(wrapper.text()).toContain('An error occurred')
  })
})
