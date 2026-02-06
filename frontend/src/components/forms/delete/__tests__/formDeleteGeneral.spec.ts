import { describe, it, expect, vi, beforeEach } from 'vitest'
import { mount } from '@vue/test-utils'
import formDeleteGeneral from '../formDeleteGeneral.vue'
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
    delete: vi.fn(),
    ACADEMIC_PERIODS: '/periods/',
    RESEARCH_SEEDBEDS: '/seedbeds/',
    INVESTIGATION_GROUPS: '/groups/',
    INVESTIGATION_GRUOPS_PROFILES: '/group-profiles/',
    RESEARCH_SEEDBEDS_PROFILES: '/seedbed-profiles/',
    RESEARCH_SEEDBEDS_MEMBERS: '/seedbed-members/',
    EXTERNAL_USER_PROFILES: '/external-profiles/',
    USERS_DIRI: '/users/diri/',
  }
}))

// Mock FeedbackToast
vi.mock('@/composables/useFeedbackToast', () => ({
  useFeedbackToast: () => ({
    showError: vi.fn(),
    showSuccess: vi.fn()
  })
}))

// Mock alert
global.alert = vi.fn()

describe('formDeleteGeneral.vue', () => {
  beforeEach(() => {
    vi.clearAllMocks()
  })

  it('renders delete confirmation card with correct title', () => {
    const wrapper = mount(formDeleteGeneral, {
      global: {
        plugins: [vuetify],
      },
      props: {
        type: 'period',
        name: 'Periodo 2024',
        label: 'periodo',
        index: 1
      }
    })

    expect(wrapper.find('.v-card-title').text()).toContain('Eliminar periodo')
  })

  it('displays the item name in confirmation text', () => {
    const wrapper = mount(formDeleteGeneral, {
      global: {
        plugins: [vuetify],
      },
      props: {
        type: 'seedbed',
        name: 'Semillero Test',
        label: 'semillero',
        index: 5
      }
    })

    expect(wrapper.text()).toContain('Semillero Test')
  })

  it('computes expectedValue correctly', () => {
    const wrapper = mount(formDeleteGeneral, {
      global: {
        plugins: [vuetify],
      },
      props: {
        type: 'group',
        name: 'Grupo A',
        label: 'grupo',
        index: 3
      }
    })

    const vm = wrapper.vm as any
    expect(vm.expectedValue).toBe('eliminar Grupo A')
  })

  it('uses alt_name if provided', () => {
    const wrapper = mount(formDeleteGeneral, {
      global: {
        plugins: [vuetify],
      },
      props: {
        type: 'group',
        name: 'Grupo Principal',
        alt_name: 'GP',
        label: 'grupo',
        index: 3
      }
    })

    const vm = wrapper.vm as any
    expect(vm.expectedValue).toBe('eliminar GP')
  })

  it('shows alert when confirmation text does not match', async () => {
    const wrapper = mount(formDeleteGeneral, {
      global: {
        plugins: [vuetify],
      },
      props: {
        type: 'period',
        name: 'Periodo 2024',
        label: 'periodo',
        index: 1
      }
    })

    const vm = wrapper.vm as any
    vm.inputValue = 'wrong text'
    await vm.deleteItem()

    expect(global.alert).toHaveBeenCalled()
    expect(API.delete).not.toHaveBeenCalled()
  })

  it('successfully deletes item when confirmation matches', async () => {
    vi.mocked(API.delete).mockResolvedValue({ error: false })

    const wrapper = mount(formDeleteGeneral, {
      global: {
        plugins: [vuetify],
      },
      props: {
        type: 'period',
        name: 'Periodo 2024',
        label: 'periodo',
        index: 1
      }
    })

    const vm = wrapper.vm as any
    vm.inputValue = 'eliminar Periodo 2024'
    await vm.deleteItem()

    expect(API.delete).toHaveBeenCalledWith(
      '/periods/1',
      expect.any(Object)
    )
    expect(wrapper.emitted('itemDeleted')).toBeTruthy()
    expect(wrapper.emitted('itemDeleted')?.[0]).toEqual([1])
  })

  it('uses correct endpoint for different entity types', async () => {
    const testCases = [
      { type: 'seedbed', endpoint: '/seedbeds/' },
      { type: 'group', endpoint: '/groups/' },
      { type: 'period', endpoint: '/periods/' },
      { type: 'group_profile', endpoint: '/group-profiles/' },
    ]

    for (const testCase of testCases) {
      vi.clearAllMocks()
      vi.mocked(API.delete).mockResolvedValue({ error: false })

      const wrapper = mount(formDeleteGeneral, {
        global: {
          plugins: [vuetify],
        },
        props: {
          type: testCase.type,
          name: 'Test Item',
          label: 'test',
          index: 7
        }
      })

      const vm = wrapper.vm as any
      vm.inputValue = vm.expectedValue
      await vm.deleteItem()

      expect(API.delete).toHaveBeenCalledWith(
        testCase.endpoint + 7,
        expect.any(Object)
      )
    }
  })

  it('renders input field for confirmation', () => {
    const wrapper = mount(formDeleteGeneral, {
      global: {
        plugins: [vuetify],
      },
      props: {
        type: 'period',
        name: 'Test',
        label: 'test',
        index: 1
      }
    })

    const input = wrapper.find('input[type="text"]')
    expect(input.exists()).toBe(true)
  })

  it('renders delete button with loading state', () => {
    const wrapper = mount(formDeleteGeneral, {
      global: {
        plugins: [vuetify],
      },
      props: {
        type: 'period',
        name: 'Test',
        label: 'test',
        index: 1
      }
    })

    const loadingBtn = wrapper.findComponent({ name: 'LoadingBtn' })
    expect(loadingBtn.exists()).toBe(true)
    expect(loadingBtn.props('text')).toBe('Eliminar')
    expect(loadingBtn.props('color')).toBe('error')
  })

  it('emits loaded event on creation', () => {
    const wrapper = mount(formDeleteGeneral, {
      global: {
        plugins: [vuetify],
      },
      props: {
        type: 'period',
        name: 'Test',
        label: 'test',
        index: 1
      }
    })

    expect(wrapper.emitted('loaded')).toBeTruthy()
  })

  it('handles API errors gracefully', async () => {
    const mockError = { response: { data: 'Error deleting item' } }
    vi.mocked(API.delete).mockRejectedValue(mockError)

    const wrapper = mount(formDeleteGeneral, {
      global: {
        plugins: [vuetify],
      },
      props: {
        type: 'period',
        name: 'Test',
        label: 'test',
        index: 1
      }
    })

    const vm = wrapper.vm as any
    vm.inputValue = vm.expectedValue
    await vm.deleteItem()

    expect(API.delete).toHaveBeenCalled()
    expect(wrapper.emitted('itemDeleted')).toBeFalsy()
  })
})
