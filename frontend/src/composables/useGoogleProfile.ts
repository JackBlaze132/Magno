import { ref } from 'vue'
import API from '@/utils/api'

interface GoogleProfile {
  name: string
  email: string
  picture: string | null
}

/**
 * Composable for managing Google profile data with caching
 *
 * This composable implements a singleton pattern to cache Google profile data
 * and prevent duplicate API calls. This is crucial because Google's profile
 * picture URLs can hit rate limits (429 Too Many Requests) when requested
 * too frequently.
 *
 * The cached data is shared across all components using this composable,
 * so the API is only called once per session.
 */

// Singleton state - shared across all components
const profileData = ref<GoogleProfile | null>(null)
const loading = ref(false)
const error = ref<string | null>(null)

export function useGoogleProfile() {
  const fetchProfile = async () => {
    // Return cached data if already loaded
    if (profileData.value) {
      console.log('✅ Using cached profile data')
      return profileData.value
    }

    // Prevent duplicate requests
    if (loading.value) {
      console.log('⏳ Profile fetch already in progress...')
      return null
    }

    loading.value = true
    error.value = null

    const apiHeaders = {
      'API-VERSION': '1'
    }

    try {
      const response = await API.get(API.GOOGLE_DATA, apiHeaders)
      console.log('🔍 Google API response:', response)

      if (response && response[0]) {
        profileData.value = {
          name: response[0].name || 'User',
          email: response[0].email || '',
          picture: response[0].picture || null
        }

        console.log('✅ Profile data cached:', {
          name: profileData.value.name,
          email: profileData.value.email,
          hasPicture: !!profileData.value.picture
        })

        return profileData.value
      } else {
        console.warn('⚠️ No profile data received')
        profileData.value = {
          name: 'User',
          email: '',
          picture: null
        }
        return profileData.value
      }
    } catch (err) {
      console.error('❌ Error fetching Google profile:', err)
      error.value = 'Failed to load profile'
      profileData.value = {
        name: 'User',
        email: 'Not available',
        picture: null
      }
      return profileData.value
    } finally {
      loading.value = false
    }
  }

  const clearCache = () => {
    profileData.value = null
    error.value = null
  }

  return {
    profileData,
    loading,
    error,
    fetchProfile,
    clearCache
  }
}
