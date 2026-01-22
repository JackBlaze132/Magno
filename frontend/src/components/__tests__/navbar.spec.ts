import { describe, it, expect } from 'vitest'
import { mount } from '@vue/test-utils'
import navbar from '../navbar.vue'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'

const vuetify = createVuetify({
  components,
  directives,
})

describe('navbar.vue', () => {
  it('renders app title "Magno"', () => {
    const wrapper = mount(navbar, {
      global: {
        plugins: [vuetify],
      },
    })
    expect(wrapper.find('h3').text()).toBe('Magno')
  })

  it('contains a login button', () => {
    const wrapper = mount(navbar, {
      global: {
        plugins: [vuetify],
      },
    })
    const loginBtn = wrapper.findComponent({ name: 'VBtn' })
    expect(loginBtn.exists()).toBe(true)
    expect(loginBtn.text()).toContain('login')
  })
})
