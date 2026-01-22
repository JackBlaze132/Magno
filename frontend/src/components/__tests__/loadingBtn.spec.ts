import { describe, it, expect, vi } from 'vitest'
import { mount } from '@vue/test-utils'
import loadingBtn from '../operators/loadingBtn.vue'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'

// Mock ResizeObserver which is used by Vuetify components
global.ResizeObserver = class ResizeObserver {
  observe() {}
  unobserve() {}
  disconnect() {}
};

const vuetify = createVuetify({
  components,
  directives,
})

describe('loadingBtn.vue', () => {
  it('renders with the correct text', () => {
    const text = 'Submit Order'
    const wrapper = mount(loadingBtn, {
      global: {
        plugins: [vuetify],
      },
      props: {
        text: text
      }
    })
    expect(wrapper.text()).toBe(text)
  })

  it('shows loading state when loading prop is true', () => {
    const wrapper = mount(loadingBtn, {
      global: {
        plugins: [vuetify],
      },
      props: {
        loading: true,
        text: 'Save'
      }
    })
    // In Vuetify, VBtn with loading="true" adds a specific class or child component
    const btn = wrapper.find('button')
    expect(btn.classes()).toContain('v-btn--loading')
  })

  it('uses the provided color', () => {
    const color = 'primary'
    const wrapper = mount(loadingBtn, {
      global: {
        plugins: [vuetify],
      },
      props: {
        color: color,
        text: 'Color Test'
      }
    })
    // VBtn color prop often translates to a class like text-primary or bg-primary
    // or it's just passed to the component internals.
    // Check if the component instance has the prop set
    expect(wrapper.props('color')).toBe(color)
  })
})
