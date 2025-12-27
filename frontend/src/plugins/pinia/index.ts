import type { App } from 'vue'
import { createPinia } from 'pinia'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'

export default function (app: App) {
  const pinia = createPinia()

  // Add persistence plugin
  pinia.use(piniaPluginPersistedstate)

  app.use(pinia)

  console.log('✅ Pinia store initialized with persistence')
}
