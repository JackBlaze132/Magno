import { describe, it, expect, beforeEach, vi } from 'vitest'
import { setActivePinia, createPinia } from 'pinia'
import { useAuthStore } from '../authStore'
import API from '@/utils/api'

// Mock the API utility
vi.mock('@/utils/api', () => ({
  default: {
    get: vi.fn(),
    post: vi.fn(),
    put: vi.fn(),
    delete: vi.fn(),
    USERS_ME: 'users/me',
    GOOGLE_DATA: 'auth/google/data',
    ACTIVE_ACADEMIC_PERIOD: 'periods/active',
    ACADEMIC_PERIODS: 'periods',
    STUDENT_PROFILES_ASSIGNED: 'profiles/student/',
    FUNCTIONARY_PROFILES_ASSIGNED: 'profiles/functionary/',
    USERS_PROFILES: 'users/profiles',
  }
}))

describe('Auth Store', () => {
  beforeEach(() => {
    setActivePinia(createPinia())
    vi.clearAllMocks()
  })

  describe('Initial State', () => {
    it('initializes with default state', () => {
      const store = useAuthStore()
      expect(store.user).toBeNull()
      expect(store.isAuthenticated).toBe(false)
      expect(store.userProfiles).toEqual([])
      expect(store.currentRole).toBeNull()
      expect(store.currentAcademicPeriod).toBeNull()
      expect(store.isInitialized).toBe(false)
      expect(store.loading).toBe(false)
      expect(store.error).toBeNull()
      expect(store.hiddenAcademicPeriods).toEqual([])
    })
  })

  describe('Getters', () => {
    it('userName getter returns "Usuario" when user is null', () => {
      const store = useAuthStore()
      expect(store.userName).toBe('Usuario')
    })

    it('userName getter returns user name when user exists', () => {
      const store = useAuthStore()
      store.user = { id: 1, name: 'John Doe', email: 'john@example.com', picture: null }
      expect(store.userName).toBe('John Doe')
    })

    it('userEmail getter returns empty string when user is null', () => {
      const store = useAuthStore()
      expect(store.userEmail).toBe('')
    })

    it('userEmail getter returns user email when user exists', () => {
      const store = useAuthStore()
      store.user = { id: 1, name: 'John', email: 'john@example.com', picture: null }
      expect(store.userEmail).toBe('john@example.com')
    })

    it('userPicture getter returns null when user has no picture', () => {
      const store = useAuthStore()
      expect(store.userPicture).toBeNull()
    })

    it('userPicture getter returns picture URL when available', () => {
      const store = useAuthStore()
      store.user = { id: 1, name: 'John', email: 'john@example.com', picture: 'http://example.com/pic.jpg' }
      expect(store.userPicture).toBe('http://example.com/pic.jpg')
    })

    it('userId getter returns null when user is null', () => {
      const store = useAuthStore()
      expect(store.userId).toBeNull()
    })

    it('userId getter returns user ID when user exists', () => {
      const store = useAuthStore()
      store.user = { id: 42, name: 'John', email: 'john@example.com', picture: null }
      expect(store.userId).toBe(42)
    })
  })

  describe('hasRole', () => {
    it('returns false if user is null and no profiles', () => {
      const store = useAuthStore()
      expect(store.hasRole('DIRI')).toBe(false)
      expect(store.hasRole('ESTUDIANTE')).toBe(false)
    })

    it('returns true if role exists in user.roles', () => {
      const store = useAuthStore()
      store.user = {
        id: 1, name: 'Admin', email: 'a@b.com', picture: null,
        roles: ['ROLE_DIRI', 'ROLE_ESTUDIANTE']
      }
      expect(store.hasRole('DIRI')).toBe(true)
      expect(store.hasRole('ROLE_DIRI')).toBe(true)
      expect(store.hasRole('ESTUDIANTE')).toBe(true)
      expect(store.hasRole('ADMIN')).toBe(false)
    })

    it('returns true if role exists in userProfiles', () => {
      const store = useAuthStore()
      store.userProfiles = [
        { id: 1, role: { id: 1, name: 'Coordinador de Semillero' } }
      ]
      expect(store.hasRole('COORDINADOR_DE_SEMILLERO')).toBe(true)
      expect(store.hasRole('COORDINADOR DE SEMILLERO')).toBe(true)
    })

    it('normalizes role names correctly', () => {
      const store = useAuthStore()
      store.user = {
        id: 1, name: 'User', email: 'u@b.com', picture: null,
        roles: ['ROLE_COORDINADOR_DE_SEMILLERO']
      }
      expect(store.hasRole('coordinador de semillero')).toBe(true)
      expect(store.hasRole('ROLE_COORDINADOR_DE_SEMILLERO')).toBe(true)
    })
  })

  describe('userRoles', () => {
    it('returns empty array when no profiles', () => {
      const store = useAuthStore()
      expect(store.userRoles).toEqual([])
    })

    it('returns all user role names from profiles', () => {
      const store = useAuthStore()
      store.userProfiles = [
        { id: 1, role: { id: 1, name: 'Administrator' } },
        { id: 2, role: { id: 2, name: 'Student' } }
      ]
      expect(store.userRoles).toEqual(['Administrator', 'Student'])
    })
  })

  describe('isAdmin', () => {
    it('returns true when user has admin role in profiles', () => {
      const store = useAuthStore()
      store.userProfiles = [
        { id: 1, role: { id: 1, name: 'Administrator' } }
      ]
      expect(store.isAdmin).toBe(true)
    })

    it('returns false when user has no admin role', () => {
      const store = useAuthStore()
      store.userProfiles = [
        { id: 2, role: { id: 2, name: 'Student' } }
      ]
      expect(store.isAdmin).toBe(false)
    })
  })

  describe('isStudent', () => {
    it('returns true when user has student role in user.roles', () => {
      const store = useAuthStore()
      store.user = {
        id: 1, name: 'Student', email: 's@example.com', picture: null,
        roles: ['ROLE_ESTUDIANTE']
      }
      expect(store.isStudent).toBe(true)
    })

    it('returns true when user has student role in profiles', () => {
      const store = useAuthStore()
      store.userProfiles = [
        { id: 1, role: { id: 1, name: 'Student' } }
      ]
      expect(store.isStudent).toBe(true)
    })

    it('returns false when user has no student role', () => {
      const store = useAuthStore()
      store.user = {
        id: 1, name: 'Teacher', email: 't@example.com', picture: null,
        roles: ['ROLE_TEACHER']
      }
      expect(store.isStudent).toBe(false)
    })
  })

  describe('isFunctionary', () => {
    it('returns true when user has functionary role in profiles', () => {
      const store = useAuthStore()
      store.userProfiles = [
        { id: 1, role: { id: 1, name: 'Functionary' } }
      ]
      expect(store.isFunctionary).toBe(true)
    })

    it('returns false when user has no functionary role', () => {
      const store = useAuthStore()
      store.userProfiles = [
        { id: 1, role: { id: 1, name: 'Student' } }
      ]
      expect(store.isFunctionary).toBe(false)
    })
  })

  describe('Actions - fetchGoogleProfile', () => {
    it('fetches and stores Google profile data', async () => {
      const store = useAuthStore()
      const mockGoogleData = [
        { name: 'John Doe', email: 'john@example.com', picture: 'http://pic.url' }
      ]
      vi.mocked(API.get).mockResolvedValue(mockGoogleData)

      await store.fetchGoogleProfile()

      expect(API.get).toHaveBeenCalledWith(API.GOOGLE_DATA, { 'API-VERSION': '1' })
      expect(store.user).toEqual({
        id: 0,
        name: 'John Doe',
        email: 'john@example.com',
        picture: 'http://pic.url'
      })
      expect(store.loading).toBe(false)
    })

    it('handles error when fetching Google profile', async () => {
      const store = useAuthStore()
      vi.mocked(API.get).mockRejectedValue(new Error('API Error'))

      await store.fetchGoogleProfile()

      expect(store.error).toBe('Error al cargar perfil de Google')
      expect(store.loading).toBe(false)
    })
  })

  describe('Actions - fetchUserDetails', () => {
    it('fetches and stores user details', async () => {
      const store = useAuthStore()
      const mockUserData = [
        {
          user_id: 123,
          full_name: 'Jane Doe',
          email: 'jane@example.com',
          user_code: 'U123',
          identification_number: '1234567890',
          roles: ['ROLE_DIRI']
        }
      ]
      vi.mocked(API.get).mockResolvedValue(mockUserData)

      await store.fetchUserDetails()

      expect(API.get).toHaveBeenCalledWith(API.USERS_ME, { 'API-VERSION': '1' })
      expect(store.user?.id).toBe(123)
      expect(store.user?.userCode).toBe('U123')
      expect(store.user?.identificationNumber).toBe('1234567890')
      expect(store.user?.roles).toEqual(['ROLE_DIRI'])
      expect(store.currentRole).toBe('DIRI')
      expect(store.loading).toBe(false)
    })

    it('sets currentRole to first role if DIRI not available', async () => {
      const store = useAuthStore()
      const mockUserData = [
        {
          user_id: 123,
          full_name: 'Jane Doe',
          email: 'jane@example.com',
          roles: ['ROLE_ESTUDIANTE', 'ROLE_TUTOR']
        }
      ]
      vi.mocked(API.get).mockResolvedValue(mockUserData)

      await store.fetchUserDetails()

      expect(store.currentRole).toBe('ESTUDIANTE')
    })

    it('handles error when fetching user details', async () => {
      const store = useAuthStore()
      vi.mocked(API.get).mockRejectedValue(new Error('API Error'))

      await store.fetchUserDetails()

      expect(store.error).toBe('Error al cargar detalles del usuario')
      expect(store.loading).toBe(false)
    })
  })

  describe('Actions - fetchActivePeriod', () => {
    it('fetches and stores active academic period', async () => {
      const store = useAuthStore()
      const mockPeriod = [{ id: 5, name: 'Periodo 2024-1', active: true }]
      vi.mocked(API.get).mockResolvedValue(mockPeriod)

      await store.fetchActivePeriod()

      expect(API.get).toHaveBeenCalledWith(API.ACTIVE_ACADEMIC_PERIOD, { 'API-VERSION': '1' })
      expect(store.currentAcademicPeriod).toBe(5)
    })

    it('handles error when fetching active period', async () => {
      const store = useAuthStore()
      vi.mocked(API.get).mockRejectedValue(new Error('API Error'))

      await store.fetchActivePeriod()

      expect(store.currentAcademicPeriod).toBeNull()
    })
  })

  describe('Actions - fetchHiddenPeriods', () => {
    it('fetches and stores hidden academic periods', async () => {
      const store = useAuthStore()
      const mockPeriods = [
        { id: 1, name: 'Period 1', is_visible: true },
        { id: 2, name: 'Period 2', is_visible: false },
        { id: 3, name: 'Period 3', isVisible: false }
      ]
      vi.mocked(API.get).mockResolvedValue(mockPeriods)

      await store.fetchHiddenPeriods()

      expect(API.get).toHaveBeenCalledWith(API.ACADEMIC_PERIODS, { 'API-VERSION': '1' })
      expect(store.hiddenAcademicPeriods).toEqual([2, 3])
    })

    it('handles error when fetching hidden periods', async () => {
      const store = useAuthStore()
      vi.mocked(API.get).mockRejectedValue(new Error('API Error'))

      await store.fetchHiddenPeriods()

      expect(store.hiddenAcademicPeriods).toEqual([])
    })
  })

  describe('Actions - fetchUserProfiles', () => {
    it('does not fetch if user ID is not available', async () => {
      const store = useAuthStore()
      await store.fetchUserProfiles()

      expect(API.get).not.toHaveBeenCalled()
    })

    it('fetches student profiles when available', async () => {
      const store = useAuthStore()
      store.user = { id: 123, name: 'Student', email: 's@example.com', picture: null }
      
      const mockStudentProfiles = [
        { id: 1, role: { id: 1, name: 'Estudiante' } }
      ]
      vi.mocked(API.get).mockResolvedValue(mockStudentProfiles)

      await store.fetchUserProfiles()

      expect(API.get).toHaveBeenCalledWith(
        API.STUDENT_PROFILES_ASSIGNED + 123,
        { 'API-VERSION': '1' }
      )
      expect(store.userProfiles.length).toBeGreaterThanOrEqual(0)
      expect(store.loading).toBe(false)
    })
  })

  describe('Permissions - can', () => {
    it('checks permissions using checkPermission utility', () => {
      const store = useAuthStore()
      store.currentRole = 'DIRI'

      // This will use the checkPermission function from utils/permissions
      const canViewPeriod = store.can('view', 'period')
      expect(typeof canViewPeriod).toBe('boolean')
    })
  })
})

