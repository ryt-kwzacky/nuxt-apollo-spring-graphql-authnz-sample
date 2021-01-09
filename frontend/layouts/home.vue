<template>
  <div>
    <Nuxt />
  </div>
</template>

<script lang="ts">
import Vue from 'vue'
import { isTryingToLogin } from '~/auth/login.ts'
export default Vue.extend({
  beforeMount() {
    console.log('----- top page before mount -----')
    // セッションを見てauthPageに飛ばす処理
    if (isTryingToLogin()) {
      this.$nuxt.$router.push('/auth')
    }
  },
})
</script>

<style lang="scss" scoped></style>
