import { describe, it, expect } from 'vitest'
import { mount } from '@vue/test-utils'
import HelloWorld from '../HelloWorld.vue'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'

const vuetify = createVuetify({
  components,
  directives,
})

describe('HelloWorld.vue', () => {
  it('renders welcome message', () => {
    const wrapper = mount(HelloWorld, {
      global: {
        plugins: [vuetify],
      },
    })
    expect(wrapper.text()).toContain('Welcome to')
    expect(wrapper.text()).toContain('Vuetify')
  })

  it('renders the "Get started" section', () => {
    const wrapper = mount(HelloWorld, {
      global: {
        plugins: [vuetify],
      },
    })
    expect(wrapper.text()).toContain('Get started')
  })
})
