import { defineStore } from 'pinia'
import API from '@/utils/api'
import { checkPermission } from '@/utils/permissions'
import type { ActionType, EntityType } from '@/utils/abstract-forms-factory/form-types/formsTypes'

/**
 * Represents the fundamental user data in the system.
 */
interface User {
  /** Unique internal identifier */
  id: number
  /** Full name provided by Google or system */
  name: string
  /** Institutional or personal email */
  email: string
  /** URL to the user's avatar image */
  picture: string | null
  /** Specific university code (e.g., student ID) */
  userCode?: string
  /** National identification number */
  identificationNumber?: string
  /** Array of raw role strings assigned to the user */
  roles?: string[]
}

/**
 * Represents a user's professional profile within the organization.
 * Links a user to a specific role and optionally an academic period.
 */
interface UserProfile {
  /** Profile unique identifier */
  id: number
  /** Role details associated with this profile */
  role: {
    id: number
    name: string
  }
  /** Academic period this profile is relevant for */
  academic_period?: {
    id: number
    name: string
  }
}

/**
 * Authentication and Authorization Store.
 * Centralizes all logic regarding user sessions, role management, and permissions.
 */
export const useAuthStore = defineStore('auth', {
  /**
   * Initial state of the authentication store.
   */
  state: () => ({
    /** Current authenticated user data */
    user: null as User | null,
    /** List of all profile/role assignments for the user */
    userProfiles: [] as UserProfile[],
    /** Currently active role name (determines permissions) */
    currentRole: null as string | null,
    /** ID of the currently active academic period in the system */
    currentAcademicPeriod: null as number | null,
    /** Whether the user is correctly authenticated */
    isAuthenticated: false,
    /** Whether the initial auth check/loading has completed */
    isInitialized: false,
    /** Global loading state for auth operations */
    loading: false,
    /** Last recorded error message */
    error: null as string | null,
    /** List of academic period IDs that are marked as hidden by admin */
    hiddenAcademicPeriods: [] as number[],
  }),

  getters: {
    /**
     * Returns the user's full name or a default placeholder.
     * @returns {string}
     */
    userName: (state) => state.user?.name || 'Usuario',

    /**
     * Returns the user's email address.
     * @returns {string}
     */
    userEmail: (state) => state.user?.email || '',

    /**
     * Returns the user's profile picture URL or null if not available.
     * @returns {string|null}
     */
    userPicture: (state) => state.user?.picture || null,

    /**
     * Returns the unique internal database ID of the user.
     * @returns {number|null}
     */
    userId: (state) => state.user?.id || null,

    /**
     * Checks if the user possesses a specific role, either in their user information
     * or across their various profiles.
     * @param {string} roleName - The name of the role to check (e.g., 'ADMIN', 'STUDENT').
     * @returns {boolean}
     */
    hasRole: (state) => (roleName: string) => {
      const normalizedSearch = roleName.toUpperCase().replace(/\s+/g, '_').replace('ROLE_', '')

      // Check in user.roles (from USERS_ME)
      if (state.user?.roles) {
        return state.user.roles.some(role =>
          role.toUpperCase().replace(/\s+/g, '_').replace('ROLE_', '') === normalizedSearch
        )
      }

      // Fallback to userProfiles (legacy/profiles)
      return state.userProfiles.some(profile =>
        profile.role.name.toUpperCase().replace(/\s+/g, '_') === normalizedSearch
      )
    },

    /**
     * Returns a list of all role names associated with the user's profiles.
     * @returns {string[]}
     */
    userRoles: (state) => {
      return state.userProfiles.map(profile => profile.role.name)
    },

    /**
     * Quick check to see if the user has administrative privileges.
     * @returns {boolean}
     */
    isAdmin: (state) => {
      return state.userProfiles.some(profile =>
        profile.role.name.toLowerCase().includes('admin')
      )
    },

    /**
     * Quick check to see if the user has a student role.
     * @returns {boolean}
     */
    isStudent: (state) => {
      // Check in user.roles first (from USERS_ME)
      if (state.user?.roles) {
        const hasStudentRole = state.user.roles.some(role =>
          role.toUpperCase().includes('ESTUDIANTE') ||
          role.toUpperCase().includes('STUDENT')
        )
        if (hasStudentRole) return true
      }

      // Fallback to userProfiles
      return state.userProfiles.some(profile =>
        profile.role.name.toLowerCase().includes('student') ||
        profile.role.name.toLowerCase().includes('estudiante')
      )
    },

    /**
     * Quick check to see if the user is a functionary (staff/professor).
     * @returns {boolean}
     */
    isFunctionary: (state) => {
      return state.userProfiles.some(profile =>
        profile.role.name.toLowerCase().includes('functionary') ||
        profile.role.name.toLowerCase().includes('funcionario')
      )
    },

    /**
     * Evaluates if the current role permits an action on a specific entity type.
     * Useful for UI conditional rendering (e.g., hiding a "Delete" button).
     * @param {ActionType} action - The action to perform (create, view, edit, delete).
     * @param {EntityType} entity - The target entity (user, period, group, etc.).
     * @returns {boolean}
     */
    can: (state) => (action: ActionType, entity: EntityType) => {
      return checkPermission(state.currentRole, action, entity)
    }
  },

  actions: {
    /**
     * Retrieves the current user's profile information from Google OAuth.
     * This is primarily used to get the user's name, email, and profile picture.
     * @returns {Promise<void>}
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
     * Fetches detailed internal user information, including the unique user ID,
     * roles assigned in the backend, and institutional codes.
     * @returns {Promise<void>}
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
     * Retrieves the current active academic period from the backend.
     * Used to scope most operations (profiles, groups, etc.) to the present time.
     * @returns {Promise<void>}
     */
    async fetchActivePeriod() {
      const headers = {
        'API-VERSION': '1'
      }

      try {
        const response = await API.get(API.ACTIVE_ACADEMIC_PERIOD, headers)
        if (response && response[0]) {
          this.currentAcademicPeriod = response[0].id
          console.log('✅ Active academic period loaded:', this.currentAcademicPeriod)
        }
      } catch (error) {
        console.error('❌ Error fetching active academic period:', error)
      }
    },

    /**
     * Fetches all academic periods that are marked as invisible.
     * Used by navigation guards to prevent users from accessing routes associated
     * with hidden periods.
     * @returns {Promise<void>}
     */
    async fetchHiddenPeriods() {
      const headers = {
        'API-VERSION': '1'
      }

      try {
        const response = await API.get(API.NOT_VISIBLE_ACADEMIC_PERIODS, headers)
        if (Array.isArray(response)) {
          this.hiddenAcademicPeriods = response
            .filter((p: any) => p.is_visible === false)
            .map((p: any) => p.id)
          console.log('✅ Hidden academic periods loaded:', this.hiddenAcademicPeriods)
        }
      } catch (error) {
        console.error('❌ Error fetching academic periods for visibility check:', error)
      }
    },

    /**
     * Fetches the user's multiple profiles (e.g., as a student in different periods).
     * @returns {Promise<void>}
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
     * Verifies if the current session token stored by the API utility is still valid.
     * @returns {Promise<boolean>} True if valid, false otherwise.
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
     * Main initialization orchestration for the authentication process.
     * Verifies the token, fetches identity information, roles, and system context (periods).
     * Should be called by the router on initial load or page reloads.
     * @returns {Promise<void>}
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

        // Fetch active academic period
        await this.fetchActivePeriod()

        // Fetch hidden academic periods
        await this.fetchHiddenPeriods()

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
     * Manually switches the active role for the user session.
     * This affects the permissions evaluated throughout the application.
     * @param {string} roleName - The target role name.
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
     * Clears all local state and triggers the API logout process.
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
     * Resets the error state in the store.
     */
    clearError() {
      this.error = null
    }
  },

  // Persist state to localStorage
  persist: {
    key: 'magno-auth',
    storage: localStorage,
    pick: ['user', 'userProfiles', 'currentRole', 'isAuthenticated', 'currentAcademicPeriod']
  }
})
