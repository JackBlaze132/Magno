/**
 * Vuetify plugin initialization.
 * Configures the UI framework with custom themes, icons, and default component properties.
 */

import type { App } from 'vue'
import { createVuetify } from 'vuetify'
import { VBtn } from 'vuetify/components/VBtn'
import defaults from './defaults'
import { icons } from './icons'
import { VDateInput } from 'vuetify/labs/VDateInput'
import { themes } from './theme'

import 'vuetify/styles'

/**
 * Initializes Vuetify and attaches it to the Vue application instance.
 * Sets up aliases (like IconBtn), component defaults, icon sets, and themes.
 *
 * @param {App} app - The Vue application instance.
 */
export default function (app: App) {
  const vuetify = createVuetify({
    /** Custom component aliases for readability and consistent styling */
    aliases: {
      IconBtn: VBtn,
    },
    /** Global component default property overrides */
    defaults,
    /** Icon configuration (using Iconify as the default set) */
    icons,
    /** Theme configuration, retrieving saved user preference from localStorage */
    theme: {
      defaultTheme: localStorage.getItem('selectedTheme') || 'light',
      themes
    },
    /** Additional components, including experimental or labs features */
    components:{
      VDateInput
    }
  })

  app.use(vuetify)
}
