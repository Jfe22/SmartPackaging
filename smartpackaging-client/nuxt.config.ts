// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  devtools: { enabled: true },
  css: [
    '~/assets/css/default.css'
  ],
  runtimeConfig: {
    public: {
      API_URL: process.env.API_URL || 'http://localhost:8080/smartpackaging/api',
    }
  }
})
