import { defineStore } from 'pinia'
import API from '@/utils/api'

interface User {
  id: number
  name: string
  email: string
  picture: string | null
  userCode?: string
  identificationNumber?: string
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
      return state.userProfiles.some(profile =>
        profile.role.name.toLowerCase() === roleName.toLowerCase()
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
          } else {
            this.user = {
              id: userData.user_id || userData.id,
              name: userData.full_name || 'User',
              email: userData.email || '',
              picture: null,
              userCode: userData.user_code,
              identificationNumber: userData.identification_number
            }
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
     * Initialize authentication - fetch all user data
     */
    async initializeAuth() {
      try {
        // Fetch Google profile first (has picture)
        await this.fetchGoogleProfile()

        // Then fetch user details (has user ID)
        await this.fetchUserDetails()

        // Finally fetch profiles with roles
        if (this.user?.id) {
          await this.fetchUserProfiles()
          this.isAuthenticated = true
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
      }
    },

    /**
     * Set current active role
     */
    setCurrentRole(roleName: string) {
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
    paths: ['user', 'userProfiles', 'currentRole', 'isAuthenticated']
  }
})
