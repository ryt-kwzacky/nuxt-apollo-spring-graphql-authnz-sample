// .envファイルを読み込むための設定
// @see https://github.com/nuxt-community/dotenv-module
require('dotenv').config()

const {
  FIREBASE_API_KEY,
  FIREBASE_AUTH_DOMAIN,
  FIREBASE_DATABASE_URL,
  FIREBASE_PROJECT_ID,
  FIREBASE_STORAGE_BUCKET,
  FIREBASE_MESSAGING_SENDER_ID,
  FIREBASE_APP_ID,
} = process.env

export default {
  // Disable server-side rendering (https://go.nuxtjs.dev/ssr-mode)
  ssr: false,

  // Target (https://go.nuxtjs.dev/config-target)
  target: 'static',

  // Global page headers (https://go.nuxtjs.dev/config-head)
  head: {
    title: 'frontend',
    meta: [
      { charset: 'utf-8' },
      { name: 'viewport', content: 'width=device-width, initial-scale=1' },
      { hid: 'description', name: 'description', content: '' },
    ],
    link: [{ rel: 'icon', type: 'image/x-icon', href: '/favicon.ico' }],
  },

  // Global CSS (https://go.nuxtjs.dev/config-css)
  css: [],

  // Plugins to run before rendering page (https://go.nuxtjs.dev/config-plugins)
  plugins: [
    /*
     * Firebase Javascript SDKの初期化用のプラグイン設定
     */
    '~/plugins/firebase',
  ],

  // Auto import components (https://go.nuxtjs.dev/config-components)
  components: true,

  // Modules for dev and build (recommended) (https://go.nuxtjs.dev/config-modules)
  buildModules: [
    // https://go.nuxtjs.dev/typescript
    '@nuxt/typescript-build',
  ],

  // Modules (https://go.nuxtjs.dev/config-modules)
  modules: [
    // https://go.nuxtjs.dev/axios
    '@nuxtjs/axios',
    '@nuxtjs/apollo',
    ['@nuxtjs/dotenv', { path: '' }],
  ],

  // Axios module configuration (https://go.nuxtjs.dev/config-axios)
  axios: {},

  // Apollo module configuration (https://qiita.com/ryo511/items/0938aefff843fd49d2d1)
  apollo: {
    clientConfigs: {
      default: {
        httpEndpoint: 'http://localhost:8080/',
      },
    },
  },
  env: {
    FIREBASE_API_KEY: FIREBASE_API_KEY || '',
    FIREBASE_AUTH_DOMAIN: FIREBASE_AUTH_DOMAIN || '',
    FIREBASE_DATABASE_URL: FIREBASE_DATABASE_URL || '',
    FIREBASE_PROJECT_ID: FIREBASE_PROJECT_ID || '',
    FIREBASE_STORAGE_BUCKET: FIREBASE_STORAGE_BUCKET || '',
    FIREBASE_MESSAGING_SENDER_ID: FIREBASE_MESSAGING_SENDER_ID || '',
    FIREBASE_APP_ID: FIREBASE_APP_ID || '',
  },

  // Build Configuration (https://go.nuxtjs.dev/config-build)
  build: {},
}
