/**
 * main.ts
 *
 * Bootstraps Vuetify and other plugins then mounts the App`
 */

// Plugins
import { registerPlugins } from '@/plugins/plugins'


// Components
import App from './App.vue'

// Composables
import { createApp } from 'vue'
//import { router } from './rrrouter'
import '@styles/styles.scss'


const app = createApp(App)

//app.use(router)

registerPlugins(app)

app.mount('#app')
