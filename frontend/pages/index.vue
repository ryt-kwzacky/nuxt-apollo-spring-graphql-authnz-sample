<template>
  <div>
    <div>
      <button @click="fireQuery">Query</button>
    </div>
    <div>
      <button @click="login">Login</button>
      <button @click="signOut">Logout</button>
      <button @click="showUserInfo">User Info</button>
      <button @click="getIdToken">IdToken</button>
      <button @click="getRedirectResult">getRedirectResult</button>
      <button @click="onAuthStateChanged">onAuthStateChanged</button>
    </div>
    <div>
      <button @click="showRoute">route</button>
      <button @click="showRoutePath">route.path</button>
      <button @click="showRouteFullPath">route.fullPath</button>
      <button @click="showRouteQuery">route.query</button>
      <button @click="showRouteParams">route.params</button>
    </div>
    <div>
      <button @click="redirectWithQuery">redirect to here with query</button>
      <button><NuxtLink to="/loggedIn">LoggedIn</NuxtLink></button>
      <button><NuxtLink to="/auth">Auth</NuxtLink></button>
      <button>
        <NuxtLink to="/loggedInWithQuery">loggedInWithQuery</NuxtLink>
      </button>
    </div>
  </div>
</template>

<script lang="ts">
import Vue from 'vue'
import gql from 'graphql-tag'
import ApolloClient from 'apollo-boost'
import { loginGoogle } from '~/auth/login.ts'
import { auth } from '~/plugins/firebase'

const book = gql`
  query {
    searchBookById(id: 1) {
      id
      name
    }
  }
`

export default Vue.extend({
  layout: 'home',
  methods: {
    login() {
      console.log('----- login -----')
      // session storage
      loginGoogle()
    },
    showUserInfo() {
      console.log('----- user info -----')
      console.log(auth.currentUser)
      console.log(auth.currentUser?.displayName)
      console.log(auth.currentUser?.uid)
    },
    async signOut() {
      console.log('----- sign out -----')
      await auth.signOut()
      console.log('----- done sign out -----')
    },
    async getIdToken() {
      console.log('----- get id token -----')
      const authUser: firebase.User | null = auth.currentUser

      if (authUser === null) {
        console.log('----- no user -----')
        return {}
      }

      const idToken = await authUser.getIdToken()
      console.log('----- idToken -----')
      console.log(idToken)
      return idToken
    },
    getRedirectResult() {
      console.log('----- getRedirectResult -----')

      auth.getRedirectResult().then((result) => {
        console.log(result)
        console.log(result.credential)
        console.log(result.user?.displayName)
        console.log(result.user?.uid)
      })
    },
    onAuthStateChanged() {
      auth.onAuthStateChanged((authUser: firebase.User | null) => {
        console.log('----- onAuthStateChanged -----')
        console.log('authUser')
        console.log(authUser)

        if (authUser !== null) {
          authUser.getIdToken().then((idToken: any) => {
            console.log('idToken')
            console.log(idToken)
          })
        }
      })
    },
    showRoute() {
      console.log(this.$route)
    },
    showRoutePath() {
      console.log(this.$route.path)
    },
    showRouteFullPath() {
      console.log(this.$route.fullPath)
    },
    showRouteQuery() {
      console.log(this.$route.query)
    },
    showRouteParams() {
      console.log(this.$route.params)
    },
    async fireQuery() {
      const client = await this.buildApolloClient()
      client
        .query({
          query: book,
        })
        .then((result: any) => {
          console.log(result)
        })
    },
    redirectWithQuery() {
      this.$router.push(`/?testQuery=${encodeURIComponent('test')}`)
    },
    async buildApolloClient(): Promise<any> {
      const idToken = await this.getIdToken()
      const client = new ApolloClient({
        uri: 'http://localhost:8080/graphql',
        request: (operation) => {
          operation.setContext({
            headers: {
              authorization: `Bearer ${idToken}`,
            },
          })
        },
      })
      console.log('----- apollo client -----')
      console.log(client)
      return client
    },
  },
})
</script>

<style lang="scss" scoped></style>
