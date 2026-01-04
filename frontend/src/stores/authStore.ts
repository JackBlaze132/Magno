import { defineStore } from 'pinia'
import API from '@/utils/api'
import { checkPermission } from '@/utils/permissions'
import type { ActionType, EntityType } from '@/utils/abstract-forms-factory/form-types/formsTypes'

interface User {
  id: number
  name: string
  email: string
  picture: string | null
  userCode?: string
  identificationNumber?: string
  roles?: string[]
}

interface UserProfile {
  id: number
  role: {
    id: number
    name: string
  }
  academic_period?: {
    id: number
    name: string
  }
}

export const useAuthStore = defineStore('auth', {
  state: () => ({
    user: null as User | null,
    userProfiles: [] as UserProfile[],
    currentRole: null as string | null,
    isAuthenticated: false,
    isInitialized: false,
    loading: false,
    error: null as string | null,
  }),

  getters: {
    /**
     * Get user's full name
     */
    userName: (state) => state.user?.name || 'Usuario',

    /**
     * Get user's email
     */
    userEmail: (state) => state.user?.email || '',

    /**
     * Get user's profile picture URL
     */
    userPicture: (state) => state.user?.picture || null,

    /**
     * Get user's ID
     */
    userId: (state) => state.user?.id || null,

    /**
     * Check if user has a specific role
     */
    hasRole: (state) => (roleName: string) => {
      const normalizedSearch = roleName.toUpperCase().replace('ROLE_', '')

      // Check in user.roles (from USERS_ME)
      if (state.user?.roles) {
        return state.user.roles.some(role =>
          role.toUpperCase().replace('ROLE_', '') === normalizedSearch
        )
      }

      // Fallback to userProfiles (legacy/profiles)
      return state.userProfiles.some(profile =>
        profile.role.name.toUpperCase().replace(/\s+/g, '_') === normalizedSearch
      )
    },

    /**
     * Get all user roles
     */
    userRoles: (state) => {
      return state.userProfiles.map(profile => profile.role.name)
    },

    /**
     * Check if user is admin
     */
    isAdmin: (state) => {
      return state.userProfiles.some(profile =>
        profile.role.name.toLowerCase().includes('admin')
      )
    },

    /**
     * Check if user is a student
     */
    isStudent: (state) => {
      return state.userProfiles.some(profile =>
        profile.role.name.toLowerCase().includes('student') ||
        profile.role.name.toLowerCase().includes('estudiante')
      )
    },

    /**
     * Check if user is a functionary
     */
    isFunctionary: (state) => {
      return state.userProfiles.some(profile =>
        profile.role.name.toLowerCase().includes('functionary') ||
        profile.role.name.toLowerCase().includes('funcionario')
      )
    },

    /**
     * Check if user can perform an action on an entity
     */
    can: (state) => (action: ActionType, entity: EntityType) => {
      return checkPermission(state.currentRole, action, entity)
    }
  },

  actions: {
    /**
     * Fetch current user data from Google OAuth
     */
    async fetchGoogleProfile() {
      if (this.loading) return

      this.loading = true
      this.error = null

      const headers = {
        'API-VERSION': '1'
      }

      try {
        const response = await API.get(API.GOOGLE_DATA, headers)
        console.log('🔍 Google profile response:', response)

        if (response && response[0]) {
          // Store basic user info from Google
          const googleData = response[0]

          // Don't overwrite user.id if we already have it from USERS_ME
          if (!this.user) {
            this.user = {
              id: 0, // Temporary, will be updated by fetchUserDetails
              name: googleData.name || 'User',
              email: googleData.email || '',
              picture: googleData.picture || null
            }
          } else {
            // Update only Google-specific fields
            this.user.name = googleData.name || this.user.name
            this.user.email = googleData.email || this.user.email
            this.user.picture = googleData.picture || this.user.picture
          }

          console.log('✅ Google profile loaded:', this.user)
        } else {
          console.warn('⚠️ No Google profile data received')
        }
      } catch (error) {
        console.error('❌ Error fetching Google profile:', error)
        this.error = 'Error al cargar perfil de Google'
      } finally {
        this.loading = false
      }
    },

    /**
     * Fetch current user details including user ID
     */
    async fetchUserDetails() {
      if (this.loading) return

      this.loading = true
      this.error = null

      const headers = {
        'API-VERSION': '1'
      }

      try {
        const response = await API.get(API.USERS_ME, headers)
        console.log('🔍 User details response:', response)

        if (response && response[0]) {
          const userData = response[0]

          // Update user with complete data
          if (this.user) {
            this.user.id = userData.user_id || userData.id
            this.user.userCode = userData.user_code
            this.user.identificationNumber = userData.identification_number
            this.user.roles = userData.roles || []
          } else {
            this.user = {
              id: userData.user_id || userData.id,
              name: userData.full_name || 'User',
              email: userData.email || '',
              picture: null,
              userCode: userData.user_code,
              identificationNumber: userData.identification_number,
              roles: userData.roles || []
            }
          }

          // Set current role if roles are available and not already set
          if (this.user.roles && this.user.roles.length > 0 && !this.currentRole) {
            // Prefer DIRI if available, otherwise take the first one
            const hasDiri = this.user.roles.some(r => r.includes('DIRI'))
            this.currentRole = hasDiri ? 'DIRI' : this.user.roles[0].replace('ROLE_', '')
          }

          console.log('✅ User details loaded:', this.user)
        }
      } catch (error) {
        console.error('❌ Error fetching user details:', error)
        this.error = 'Error al cargar detalles del usuario'
      } finally {
        this.loading = false
      }
    },

    /**
     * Fetch user profiles with roles
     */
    async fetchUserProfiles() {
      if (!this.user?.id) {
        console.warn('⚠️ Cannot fetch profiles: user ID not available')
        return
      }

      this.loading = true
      this.error = null

      const headers = {
        'API-VERSION': '1'
      }

      try {
        // Try to get student profiles
        const studentProfiles = await API.get(
          API.STUDENT_PROFILES_ASSIGNED + this.user.id,
          headers
        )

        if (studentProfiles && Array.isArray(studentProfiles)) {
          this.userProfiles = studentProfiles
          console.log('✅ Student profiles loaded:', this.userProfiles)
        }

        // Set current role to the first profile's role if available
        if (this.userProfiles.length > 0 && !this.currentRole) {
          this.currentRole = this.userProfiles[0].role.name
        }
      } catch (error) {
        console.error('❌ Error fetching user profiles:', error)
        this.error = 'Error al cargar perfiles del usuario'
      } finally {
        this.loading = false
      }
    },

    /**
     * Verify if the current token/session is still valid
     */
    async verifyAuth() {
      const headers = {
        'API-VERSION': '1'
      }

      try {
        const response = await API.get(API.USERS_ME, headers)
        return !!(response && response[0])
      } catch (error) {
        console.warn('⚠️ Token invalid or expired:', error)
        return false
      }
    },

    /**
     * Initialize authentication - fetch all user data
     */
    async initializeAuth() {
      // If we think we are authenticated, verify the token first
      if (this.isAuthenticated) {
        const isValid = await this.verifyAuth()
        if (!isValid) {
          this.logout()
          return
        }
      }

      try {
        // Fetch Google profile first (has picture)
        await this.fetchGoogleProfile()

        // Then fetch user details (has user ID)
        await this.fetchUserDetails()

        // Finally fetch profiles with roles
        if (this.user?.id) {
          // If we have roles from USERS_ME, we don't strictly need fetchUserProfiles for auth
          // but we might need it for other profile data.
          // However, we should mark as authenticated if we have user data.
          if (this.user.roles && this.user.roles.length > 0) {
             this.isAuthenticated = true
          } else {
             await this.fetchUserProfiles()
             this.isAuthenticated = true
          }
        }

        console.log('✅ Authentication initialized:', {
          user: this.user,
          profiles: this.userProfiles,
          role: this.currentRole,
          authenticated: this.isAuthenticated
        })
      } catch (error) {
        console.error('❌ Error initializing authentication:', error)
        this.isAuthenticated = false
      } finally {
        this.isInitialized = true
      }
    },

    /**
     * Set current active role
     */
    setCurrentRole(roleName: string) {
      // Check in user.roles (from USERS_ME)
      if (this.user?.roles) {
        const normalizedSearch = roleName.toUpperCase().replace('ROLE_', '')
        const hasRole = this.user.roles.some(role =>
          role.toUpperCase().replace('ROLE_', '') === normalizedSearch
        )
        if (hasRole) {
          this.currentRole = normalizedSearch
          console.log('✅ Current role set to:', normalizedSearch)
          return
        }
      }

      // Fallback to userProfiles
      const hasRole = this.userProfiles.some(
        profile => profile.role.name === roleName
      )

      if (hasRole) {
        this.currentRole = roleName
        console.log('✅ Current role set to:', roleName)
      } else {
        console.warn('⚠️ User does not have role:', roleName)
      }
    },

    /**
     * Logout - clear all auth data
     */
    logout() {
      this.user = null
      this.userProfiles = []
      this.currentRole = null
      this.isAuthenticated = false
      this.isInitialized = false
      this.error = null

      // Call API logout
      API.logout()
    },

    /**
     * Clear error
     */
    clearError() {
      this.error = null
    }
  },

  // Persist state to localStorage
  persist: {
    key: 'magno-auth',
    storage: localStorage,
    pick: ['user', 'userProfiles', 'currentRole', 'isAuthenticated']
  }
})
