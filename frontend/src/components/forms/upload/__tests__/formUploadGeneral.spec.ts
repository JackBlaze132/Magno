import { describe, it, expect, vi, beforeEach } from 'vitest'
import { mount } from '@vue/test-utils'
import formUploadGeneral from '../formUploadGeneral.vue'
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
    post: vi.fn(),
    RESEARCH_SEEDBED_STUDENT_PROFILES_UPLOAD_BY_EXCEL: '/upload/seedbed-students/',
  }
}))

// Mock FeedbackToast
vi.mock('@/composables/useFeedbackToast', () => ({
  useFeedbackToast: () => ({
    showError: vi.fn(),
    showSuccess: vi.fn()
  })
}))

// Mock router
const mockRouter = {
  push: vi.fn()
}

describe('formUploadGeneral.vue', () => {
  const mockFields = [
    { 
      key: 'file', 
      label: 'Archivo', 
      type: 'drag-drop',
      accept: '.xlsx,.xls',
      required: true 
    }
  ]

  beforeEach(() => {
    vi.clearAllMocks()
  })

  it('renders upload card with correct title', () => {
    const wrapper = mount(formUploadGeneral, {
      global: {
        plugins: [vuetify],
        mocks: {
          $router: mockRouter,
          $route: { name: 'upload' }
        }
      },
      props: {
        type: 'seedbed_member',
        fields: mockFields,
        label: 'estudiantes'
      }
    })

    expect(wrapper.find('.v-card-title').text()).toBe('Subir estudiantes')
  })

  it('initializes with empty files array', () => {
    const wrapper = mount(formUploadGeneral, {
      global: {
        plugins: [vuetify],
        mocks: {
          $router: mockRouter,
          $route: { name: 'upload' }
        }
      },
      props: {
        type: 'seedbed_member',
        fields: mockFields,
        label: 'archivos'
      }
    })

    const vm = wrapper.vm as any
    expect(vm.files).toEqual([])
    expect(vm.loading).toBe(false)
  })

  it('handles files selected from child component', () => {
    const wrapper = mount(formUploadGeneral, {
      global: {
        plugins: [vuetify],
        mocks: {
          $router: mockRouter,
          $route: { name: 'upload' }
        }
      },
      props: {
        type: 'seedbed_member',
        fields: mockFields,
        label: 'archivos'
      }
    })

    const mockFiles = [
      new File(['content'], 'test.xlsx', { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' })
    ]

    const vm = wrapper.vm as any
    vm.handleFilesSelected(mockFiles)

    expect(vm.files).toEqual(mockFiles)
  })

  it('validates files before upload', () => {
    const wrapper = mount(formUploadGeneral, {
      global: {
        plugins: [vuetify],
        mocks: {
          $router: mockRouter,
          $route: { name: 'upload' }
        }
      },
      props: {
        type: 'seedbed_member',
        fields: mockFields,
        label: 'archivos'
      }
    })

    const vm = wrapper.vm as any
    
    // No files - should fail validation
    expect(vm.validateFiles()).toBe(false)

    // With files - should pass
    vm.files = [new File(['content'], 'test.xlsx')]
    expect(vm.validateFiles()).toBe(true)
  })

  it('gets correct API endpoint for upload type', () => {
    const wrapper = mount(formUploadGeneral, {
      global: {
        plugins: [vuetify],
        mocks: {
          $router: mockRouter,
          $route: { name: 'upload' }
        }
      },
      props: {
        type: 'seedbed_member',
        fields: mockFields,
        label: 'archivos'
      }
    })

    const vm = wrapper.vm as any
    const endpoint = vm.getApiEndpoint()
    expect(endpoint).toBe('/upload/seedbed-students/')
  })

  it('includes index in endpoint when provided', () => {
    const wrapper = mount(formUploadGeneral, {
      global: {
        plugins: [vuetify],
        mocks: {
          $router: mockRouter,
          $route: { name: 'upload' }
        }
      },
      props: {
        type: 'seedbed_member',
        fields: mockFields,
        label: 'archivos',
        index: 5
      }
    })

    const vm = wrapper.vm as any
    const endpoint = vm.getApiEndpoint()
    expect(endpoint).toBe('/upload/seedbed-students/5')
  })

  it('creates FormData correctly for single file', async () => {
    vi.mocked(API.post).mockResolvedValue({ error: false, data: { success: true } })

    const wrapper = mount(formUploadGeneral, {
      global: {
        plugins: [vuetify],
        mocks: {
          $router: mockRouter,
          $route: { name: 'upload' }
        }
      },
      props: {
        type: 'seedbed_member',
        fields: mockFields,
        label: 'archivos'
      }
    })

    const vm = wrapper.vm as any
    const mockFile = new File(['content'], 'test.xlsx', { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' })
    vm.files = [mockFile]

    await vm.submitFile()

    expect(API.post).toHaveBeenCalledWith(
      expect.any(String),
      expect.any(FormData),
      expect.any(Object)
    )
  })

  it('creates FormData correctly for multiple files', async () => {
    vi.mocked(API.post).mockResolvedValue({ error: false, data: { success: true } })

    const wrapper = mount(formUploadGeneral, {
      global: {
        plugins: [vuetify],
        mocks: {
          $router: mockRouter,
          $route: { name: 'upload' }
        }
      },
      props: {
        type: 'seedbed_member',
        fields: mockFields,
        label: 'archivos',
        multiple: true
      }
    })

    const vm = wrapper.vm as any
    const mockFiles = [
      new File(['content1'], 'test1.xlsx'),
      new File(['content2'], 'test2.xlsx')
    ]
    vm.files = mockFiles

    await vm.submitFile()

    expect(API.post).toHaveBeenCalled()
  })

  it('emits itemUploaded event on successful upload', async () => {
    const mockResponse = { error: false, data: { id: 1, success: true } }
    vi.mocked(API.post).mockResolvedValue(mockResponse)

    const wrapper = mount(formUploadGeneral, {
      global: {
        plugins: [vuetify],
        mocks: {
          $router: mockRouter,
          $route: { name: 'upload' }
        }
      },
      props: {
        type: 'seedbed_member',
        fields: mockFields,
        label: 'archivos'
      }
    })

    const vm = wrapper.vm as any
    vm.files = [new File(['content'], 'test.xlsx')]

    await vm.submitFile()

    expect(wrapper.emitted('itemUploaded')).toBeTruthy()
    // The component emits the entire response, not just response.data
    expect(wrapper.emitted('itemUploaded')?.[0]).toEqual([mockResponse])
  })

  it('includes additionalData in FormData', async () => {
    vi.mocked(API.post).mockResolvedValue({ error: false, data: { success: true } })

    const additionalData = { seedbedId: '123', semester: '2024-1' }
    const wrapper = mount(formUploadGeneral, {
      global: {
        plugins: [vuetify],
        mocks: {
          $router: mockRouter,
          $route: { name: 'upload' }
        }
      },
      props: {
        type: 'seedbed_member',
        fields: mockFields,
        label: 'archivos',
        additionalData
      }
    })

    const vm = wrapper.vm as any
    vm.files = [new File(['content'], 'test.xlsx')]

    await vm.submitFile()

    expect(API.post).toHaveBeenCalled()
  })

  it('handles upload errors gracefully', async () => {
    const mockError = { response: { data: 'Upload failed' } }
    vi.mocked(API.post).mockRejectedValue(mockError)

    const wrapper = mount(formUploadGeneral, {
      global: {
        plugins: [vuetify],
        mocks: {
          $router: mockRouter,
          $route: { name: 'upload' }
        }
      },
      props: {
        type: 'seedbed_member',
        fields: mockFields,
        label: 'archivos'
      }
    })

    const vm = wrapper.vm as any
    vm.files = [new File(['content'], 'test.xlsx')]

    await vm.submitFile()

    expect(wrapper.emitted('itemUploaded')).toBeFalsy()
  })

  it('validates file types when accept prop is specified', () => {
    const fieldsWithAccept = [
      { 
        key: 'file', 
        label: 'Archivo', 
        type: 'drag-drop',
        accept: '.pdf,.docx',
        required: true 
      }
    ]

    const wrapper = mount(formUploadGeneral, {
      global: {
        plugins: [vuetify],
        mocks: {
          $router: mockRouter,
          $route: { name: 'upload' }
        }
      },
      props: {
        type: 'seedbed_member',
        fields: fieldsWithAccept,
        label: 'archivos'
      }
    })

    const vm = wrapper.vm as any
    
    // Valid file type
    vm.files = [new File(['content'], 'document.pdf', { type: 'application/pdf' })]
    expect(vm.validateFiles()).toBe(true)

    // Invalid file type
    vm.files = [new File(['content'], 'image.jpg', { type: 'image/jpeg' })]
    expect(vm.validateFiles()).toBe(false)
  })

  it('emits loaded event on creation', () => {
    const wrapper = mount(formUploadGeneral, {
      global: {
        plugins: [vuetify],
        mocks: {
          $router: mockRouter,
          $route: { name: 'upload' }
        }
      },
      props: {
        type: 'seedbed_member',
        fields: mockFields,
        label: 'archivos'
      }
    })

    expect(wrapper.emitted('loaded')).toBeTruthy()
  })

  it('renders LoadingBtn with correct props', () => {
    const wrapper = mount(formUploadGeneral, {
      global: {
        plugins: [vuetify],
        mocks: {
          $router: mockRouter,
          $route: { name: 'upload' }
        }
      },
      props: {
        type: 'seedbed_member',
        fields: mockFields,
        label: 'archivos'
      }
    })

    const loadingBtn = wrapper.findComponent({ name: 'LoadingBtn' })
    expect(loadingBtn.exists()).toBe(true)
    expect(loadingBtn.props('text')).toBe('Subir')
    expect(loadingBtn.props('color')).toBe('black')
  })
})
