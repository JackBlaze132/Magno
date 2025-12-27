import 'vue-router'

declare module 'vue-router' {
  interface RouteMeta {
    /**
     * Requires authentication to access this route
     */
    requiresAuth?: boolean

    /**
     * Required role to access this route
     */
    requiredRole?: string

    /**
     * Page title
     */
    title?: string
  }
}
