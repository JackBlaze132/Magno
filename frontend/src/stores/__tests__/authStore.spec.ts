import { describe, it, expect, beforeEach, vi } from 'vitest'
import { setActivePinia, createPinia } from 'pinia'
import { useAuthStore } from '../authStore'

// Mock the API utility
vi.mock('@/utils/api', () => ({
  default: {
    get: vi.fn(),
    post: vi.fn(),
    put: vi.fn(),
    delete: vi.fn(),
    USERS_ME: 'users/me',
    USERS_PROFILES: 'users/profiles',
  }
}))

describe('Auth Store', () => {
  beforeEach(() => {
    setActivePinia(createPinia())
  })

  it('initializes with default state', () => {
    const store = useAuthStore()
    expect(store.user).toBeNull()
    expect(store.isAuthenticated).toBe(false)
    expect(store.userProfiles).toEqual([])
  })

  it('userName getter returns "Usuario" when user is null', () => {
    const store = useAuthStore()
    expect(store.userName).toBe('Usuario')
  })

  it('userName getter returns user name when user exists', () => {
    const store = useAuthStore()
    store.user = { id: 1, name: 'John Doe', email: 'john@example.com', picture: null }
    expect(store.userName).toBe('John Doe')
  })

  describe('hasRole', () => {
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
  })

  it('isAdmin identifies admin roles correctly', () => {
    const store = useAuthStore()
    store.userProfiles = [
      { id: 1, role: { id: 1, name: 'Administrator' } }
    ]
    expect(store.isAdmin).toBe(true)

    store.userProfiles = [
      { id: 2, role: { id: 2, name: 'Student' } }
    ]
    expect(store.isAdmin).toBe(false)
  })
})
