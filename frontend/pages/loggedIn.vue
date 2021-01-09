<template>
  <div>
    <p>Name: {{ userName }}</p>
    <p>Token: {{ token }}</p>
    <NuxtLink to="/">Top</NuxtLink>
  </div>
</template>

<script lang="ts">
import Vue from 'vue'
import { auth } from '~/plugins/firebase'

interface DataType {
  userName: string | null | undefined
  token: string
}

export default Vue.extend({
  middleware: ['checkLogin', 'test'],
  data(): DataType {
    return {
      userName: '',
      token: '',
    }
  },
  beforeMount() {
    this.fetchInitialData()
  },
  methods: {
    async fetchInitialData() {
      this.userName = auth.currentUser?.displayName

      const authUser: firebase.User | null = auth.currentUser
      this.token = await authUser.getIdToken()
    },
  },
})
</script>

<style lang="scss" scoped></style>
