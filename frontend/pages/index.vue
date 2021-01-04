<template>
  <div>{{ bookById }}</div>
</template>

<script lang="ts">
import Vue from 'vue'
import book from '~/apollo/queries/books.gql'

interface DataType {
  bookById: {
    id: string
    name: string
  }
}

export default Vue.extend({
  data(): DataType {
    return {
      bookById: {
        id: '',
        name: '',
      },
    }
  },

  apollo: {
    bookById: {
      prefetch: true,
      query: book,
    },
  },
})
</script>

<style lang="scss" scoped></style>
