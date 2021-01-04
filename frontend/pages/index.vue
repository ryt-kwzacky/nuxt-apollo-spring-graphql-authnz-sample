<template>
  <div>
    <p>{{ searchBookById }}</p>
    <button @click="register">Register</button>
  </div>
</template>

<script lang="ts">
import Vue from 'vue'
import gql from 'graphql-tag'

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
  methods: {
    async register() {
      const test = await this.$apollo.mutate({
        mutation: register,
      })
      console.log(test)
    },
  },
})
</script>

<style lang="scss" scoped></style>
