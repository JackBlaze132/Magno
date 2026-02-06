import { describe, it, expect, vi, beforeEach } from 'vitest'
import { mount } from '@vue/test-utils'
import formUpdateGeneral from '../formUpdateGeneral.vue'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'
import API from '@/utils/api'

// Mock ResizeObserver for Vuetify components
global.ResizeObserver = class ResizeObserver {
  observe() {}
  unobserve() {}
  disconnect() {}
}

const vuetify = createVuetify({
  components,
  directives,
})

// Mock API
vi.mock('@/utils/api', () => ({
  default: {
    put: vi.fn(),
    ACADEMIC_PERIODS: '/periods/',
    RESEARCH_SEEDBEDS: '/seedbeds/',
    INVESTIGATION_GROUPS: '/groups/',
    INVESTIGATION_GRUOPS_PROFILES: '/group-profiles/',
    RESEARCH_SEEDBEDS_PROFILES: '/seedbed-profiles/',
    RESEARCH_SEEDBEDS_MEMBERS: '/seedbed-members/',
    EXTERNAL_USER_PROFILES: '/external-profiles/',
    STUDENT_PROFILES: '/student-profiles/',
    FUNCTIONARY_PROFILES: '/functionary-profiles/',
  }
}))

// Mock FeedbackToast
vi.mock('@/composables/useFeedbackToast', () => ({
  useFeedbackToast: () => ({
    showError: vi.fn(),
    showSuccess: vi.fn()
  })
}))

describe('formUpdateGeneral.vue', () => {
  const mockFields = [
    { key: 'name', label: 'Nombre', type: 'text' },
    { key: 'active', label: 'Activo', type: 'checkbox' },
    { key: 'description', label: 'Descripción', type: 'textarea' },
    { key: 'category', label: 'Categoría', type: 'select', options: [{ label: 'A', value: 'a' }] }
  ]

  beforeEach(() => {
    vi.clearAllMocks()
  })

  it('renders the form with correct title', () => {
    const wrapper = mount(formUpdateGeneral, {
      global: {
        plugins: [vuetify],
      },
      props: {
        fields: mockFields,
        label: 'Periodo',
        type: 'period',
        index: 1,
        initialData: { name: 'Test Period' }
      }
    })

    expect(wrapper.find('.v-card-title').text()).toBe('Editar Periodo')
  })

  it('initializes formValues with initialData', () => {
    const initialData = { name: 'Test Period', active: true }
    const wrapper = mount(formUpdateGeneral, {
      global: {
        plugins: [vuetify],
      },
      props: {
        fields: mockFields,
        label: 'Periodo',
        type: 'period',
        index: 1,
        initialData
      }
    })

    const vm = wrapper.vm as any
    expect(vm.formValues.name).toBe('Test Period')
    expect(vm.formValues.active).toBe(true)
  })

  it('renders all field types correctly', () => {
    const wrapper = mount(formUpdateGeneral, {
      global: {
        plugins: [vuetify],
      },
      props: {
        fields: mockFields,
        label: 'Test',
        type: 'period',
        index: 1,
        initialData: {}
      }
    })

    // Check that field labels are rendered
    expect(wrapper.html()).toContain('Nombre')
    expect(wrapper.html()).toContain('Activo')
    expect(wrapper.html()).toContain('Descripción')
    expect(wrapper.html()).toContain('Categoría')
  })

  it('emits itemEdited event on successful update', async () => {
    const mockResponse = { error: false, data: { id: 1, name: 'Updated' } }
    vi.mocked(API.put).mockResolvedValue(mockResponse)

    const wrapper = mount(formUpdateGeneral, {
      global: {
        plugins: [vuetify],
      },
      props: {
        fields: mockFields,
        label: 'Periodo',
        type: 'period',
        index: 1,
        initialData: { name: 'Test' }
      }
    })

    await wrapper.vm.editItem()
    
    expect(API.put).toHaveBeenCalledWith(
      API.ACADEMIC_PERIODS + 1,
      expect.any(Object),
      expect.any(Object)
    )
    expect(wrapper.emitted('itemEdited')).toBeTruthy()
  })

  it('updates different entity types with correct endpoints', async () => {
    const testCases = [
      { type: 'period', endpoint: '/periods/' },
      { type: 'seedbed', endpoint: '/seedbeds/' },
      { type: 'group', endpoint: '/groups/' },
      { type: 'group_profile', endpoint: '/group-profiles/' },
    ]

    for (const testCase of testCases) {
      vi.clearAllMocks()
      vi.mocked(API.put).mockResolvedValue({ error: false })

      const wrapper = mount(formUpdateGeneral, {
        global: {
          plugins: [vuetify],
        },
        props: {
          fields: mockFields,
          label: 'Test',
          type: testCase.type,
          index: 5,
          initialData: { name: 'Test' }
        }
      })

      await wrapper.vm.editItem()

      expect(API.put).toHaveBeenCalledWith(
        testCase.endpoint + 5,
        expect.any(Object),
        expect.any(Object)
      )
    }
  })

  it('emits fieldChanged event when field value changes', async () => {
    const wrapper = mount(formUpdateGeneral, {
      global: {
        plugins: [vuetify],
      },
      props: {
        fields: mockFields,
        label: 'Test',
        type: 'period',
        index: 1,
        initialData: {}
      }
    })

    const vm = wrapper.vm as any
    vm.handleFieldChange('name', 'New Value')

    expect(wrapper.emitted('fieldChanged')).toBeTruthy()
    expect(wrapper.emitted('fieldChanged')?.[0]).toEqual(['name', 'New Value'])
  })

  it('merges additionalData with formValues', () => {
    const initialData = { name: 'Test' }
    const additionalData = { description: 'Additional' }
    
    const wrapper = mount(formUpdateGeneral, {
      global: {
        plugins: [vuetify],
      },
      props: {
        fields: mockFields,
        label: 'Test',
        type: 'period',
        index: 1,
        initialData,
        additionalData
      }
    })

    const vm = wrapper.vm as any
    expect(vm.formValues.name).toBe('Test')
    expect(vm.formValues.description).toBe('Additional')
  })

  it('emits loaded event on creation', () => {
    const wrapper = mount(formUpdateGeneral, {
      global: {
        plugins: [vuetify],
      },
      props: {
        fields: mockFields,
        label: 'Test',
        type: 'period',
        index: 1,
        initialData: {}
      }
    })

    expect(wrapper.emitted('loaded')).toBeTruthy()
  })
})
