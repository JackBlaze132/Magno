import type { App } from 'vue'
import { createRouter, createWebHistory } from 'vue-router'
import { routes } from './routes'
import { useAuthStore } from '@/stores/authStore'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
})

// Navigation guard for authentication
router.beforeEach(async (to, from, next) => {
  const authStore = useAuthStore()

  // Check if route requires authentication
  const requiresAuth = to.matched.some(record => record.meta.requiresAuth)

  // If going to login page, just allow it (don't block OAuth redirects)
  if (to.path === '/login') {
    // Only redirect away if already fully authenticated with user data
    if (authStore.isAuthenticated && authStore.user) {
      console.log('✅ Already authenticated, redirecting to home')
      next('/inicio')
      return
    }
    next()
    return
  }

  // If route requires auth, try to initialize auth if needed
  if (requiresAuth) {
    // If not initialized yet, try to initialize (might be OAuth callback or page reload)
    if (!authStore.isInitialized) {
      console.log('🔄 Initializing authentication...')
      try {
        await authStore.initializeAuth()
      } catch (error) {
        console.warn('⚠️ Authentication initialization failed, redirecting to login')
        next('/login')
        return
      }
    }

    // After init, check if authenticated
    if (!authStore.isAuthenticated) {
      console.warn('⚠️ Route requires authentication, redirecting to login')
      next('/login')
      return
    }

    // Check role-based access if specified
    if (to.meta.requiredRole) {
      const requiredRole = to.meta.requiredRole as string
      if (!authStore.hasRole(requiredRole)) {
        console.warn(`⚠️ User does not have required role: ${requiredRole}`)
        next('/inicio')
        return
      }
    }

    // Check permission-based access if specified
    if (to.meta.requiredPermission) {
      const { action, entity } = to.meta.requiredPermission as { action: any, entity: any }
      if (!authStore.can(action, entity)) {
        console.warn(`⚠️ User does not have required permission: ${action} on ${entity}`)
        next('/inicio')
        return
      }
    }
  }

  next()
})

export default function (app: App) {
  app.use(router)
}

export { router }
