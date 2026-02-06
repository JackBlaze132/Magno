import { describe, it, expect } from 'vitest'
import { mount } from '@vue/test-utils'
import navbar from '../navbar.vue'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'
import { createRouter, createMemoryHistory } from 'vue-router'

const vuetify = createVuetify({
  components,
  directives,
})

// Mock router for testing navigation
const router = createRouter({
  history: createMemoryHistory(),
  routes: [
    { path: '/', component: { template: '<div>Home</div>' } },
    { path: '/login', component: { template: '<div>Login</div>' } },
  ],
})

describe('navbar.vue', () => {
  it('renders app title "Magno"', () => {
    const wrapper = mount(navbar, {
      global: {
        plugins: [vuetify, router],
      },
    })
    expect(wrapper.find('h3').text()).toBe('Magno')
  })

  it('renders login button with correct props', () => {
    const wrapper = mount(navbar, {
      global: {
        plugins: [vuetify, router],
      },
    })
    const loginBtn = wrapper.findComponent({ name: 'VBtn' })
    expect(loginBtn.exists()).toBe(true)
    expect(loginBtn.text().toLowerCase()).toContain('login')
    expect(loginBtn.props('to')).toBe('/login')
    expect(loginBtn.props('color')).toBe('indigo-darken-1')
    expect(loginBtn.props('rounded')).toBe('xl')
  })

  it('has correct layout classes', () => {
    const wrapper = mount(navbar, {
      global: {
        plugins: [vuetify, router],
      },
    })
    const container = wrapper.find('.landing-nav-bar')
    expect(container.exists()).toBe(true)
    expect(container.classes()).toContain('d-flex')
    expect(container.classes()).toContain('align-center')
  })
})
