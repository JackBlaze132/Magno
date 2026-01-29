import { describe, it, expect, vi } from 'vitest'
import { mount } from '@vue/test-utils'
import formCreateGeneral from '../formCreateGeneral.vue'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'

const vuetify = createVuetify({
  components,
  directives,
})

// Mock FeedbackToast
vi.mock('@/composables/useFeedbackToast', () => ({
  useFeedbackToast: () => ({
    showError: vi.fn(),
    showSuccess: vi.fn()
  })
}))

describe('formCreateGeneral.vue', () => {
  const mockFields = [
    { key: 'name', label: 'Nombre', type: 'text' },
    { key: 'status', label: 'Estado', type: 'radio-group', options: [{ label: 'Activo', value: true }] },
    { key: 'category', label: 'Categoría', type: 'select', options: [{ label: 'A', value: 'a' }] }
  ]

  it('renders all fields from the schema', () => {
    const wrapper = mount(formCreateGeneral, {
      global: {
        plugins: [vuetify],
      },
      props: {
        fields: mockFields,
        label: 'Test Entity',
        type: 'test'
      }
    })

    // Check VCardTitle
    expect(wrapper.find('.v-card-title').text()).toBe('Agregar Test Entity')

    // Check if VTextField for 'name' exists (Vuetify renders a complex structure, better check by label or role)
    expect(wrapper.html()).toContain('Nombre')
    expect(wrapper.html()).toContain('Estado')
    expect(wrapper.html()).toContain('Categoría')
  })

  it('initializes formValues correctly based on fields', () => {
    const wrapper = mount(formCreateGeneral, {
      global: {
        plugins: [vuetify],
      },
      props: {
        fields: mockFields,
        label: 'Test',
        type: 'test'
      }
    })

    const vm = wrapper.vm as any
    expect(vm.formValues).toHaveProperty('name')
    expect(vm.formValues).toHaveProperty('status')
    expect(vm.formValues).toHaveProperty('category')
  })

  it('updates formValues when input changes', async () => {
    const wrapper = mount(formCreateGeneral, {
      global: {
        plugins: [vuetify],
      },
      props: {
        fields: mockFields,
        label: 'Test',
        type: 'test'
      }
    })

    const vm = wrapper.vm as any
    // Simulate typing in the name field
    const input = wrapper.find('input[type="text"]')
    await input.setValue('New Name')

    expect(vm.formValues.name).toBe('New Name')
  })
})
