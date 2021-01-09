<template>
  <div>auth</div>
</template>

<script lang="ts">
import Vue from 'vue'
import { finishTryingToLogin } from '~/auth/login.ts'
import { auth } from '~/plugins/firebase'

export default Vue.extend({
  beforeMount() {
    console.log('----- auth page before mount -----')
    auth.onAuthStateChanged((authUser: firebase.User | null) => {
      if (authUser !== null) {
        authUser.getIdToken().then((idToken: any) => {
          console.log('---- idToken ----')
          console.log(idToken)
        })

        finishTryingToLogin()
      } else {
        console.log('---- no authenticated user ----')
        finishTryingToLogin()
      }
    })
  },
})
</script>

<style lang="scss" scoped></style>
