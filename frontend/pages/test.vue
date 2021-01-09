<template>
  <div>
    <p>{{ searchBookById }}</p>
    <button @click="register">Register</button>
    <button @click="login">Login</button>
    <p>user: {{ $store.state.test.name }}</p>
    <button @click="apolloTest">apollo</button>
  </div>
</template>

<script lang="ts">
import Vue from 'vue'
import gql from 'graphql-tag'
import ApolloClient from 'apollo-boost'
import { loginGoogle } from '~/auth/login.ts'
import { auth } from '~/plugins/firebase'

const test = 'test!!!'

const client = new ApolloClient({
  uri: 'http://localhost:8080/graphql',
  request: (operation) => {
    operation.setContext({
      headers: {
        authorization: `Bearer ${test}`,
      },
    })
  },
})

const book = gql`
  query {
    searchBookById(id: 1) {
      id
      name
    }
  }
`

const register = gql`
  mutation {
    registerBook(name: "test5") {
      id
      name
    }
  }
`

interface DataType {
  searchBookById: {
    id: string
    name: string
  }
}

export default Vue.extend({
  middleware: 'test',
  data(): DataType {
    return {
      searchBookById: {
        id: '',
        name: '',
      },
    }
  },
  apollo: {
    searchBookById: {
      prefetch: true,
      query: book,
    },
  },
  beforeMount() {
    console.log('before mount')
    auth.getRedirectResult().then((result) => {
      console.log(result)
      console.log(result.credential)
      console.log(result.user?.displayName)
      console.log(result.user?.uid)
    })

    auth.onAuthStateChanged((authUser: firebase.User | null) => {
      console.log('onAuthStateChanged')
      console.log(authUser)

      if (authUser !== null) {
        authUser.getIdToken().then((idToken: any) => {
          console.log('authUser')
          console.log(authUser)
          console.log('idToken')
          console.log(idToken)
        })
      }
      console.log('ここでリダイレクトさせればok?') // というかmiddlewareでやればok
    })
  },
  methods: {
    async register() {
      const test = await this.$apollo.mutate({
        mutation: register,
      })
      console.log(test)
    },
    login() {
      console.log('login')
      // とりあえあずこれでログイン情報取れる。
      console.log(auth.currentUser)
      this.$store.commit('changeTest')
      loginGoogle()
    },
    apolloTest() {
      const book = gql`
        query {
          searchBookById(id: 1) {
            id
            name
          }
        }
      `

      client
        .query({
          query: book,
        })
        .then((result) => console.log(result))
    },
  },
})
</script>

<style lang="scss" scoped></style>
