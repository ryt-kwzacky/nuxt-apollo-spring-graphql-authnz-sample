import { auth, facebookProvider, googleProvider } from '~/plugins/firebase'

export const loginGoogle = () => {
  setTryingToLoginFlag()
  return auth.signInWithRedirect(googleProvider)
}

export const loginFacebook = () => {
  setTryingToLoginFlag()
  return auth.signInWithRedirect(facebookProvider)
}

const key = 'auth.trying_login'
const value = '1'

/*
 * ログイン開始を示すフラグを立てる処理
 */
const setTryingToLoginFlag = (): void => {
  sessionStorage.setItem(key, value)
}

/*
 * ログイン中かどうかを判定する処理
 */
export const isTryingToLogin = (): boolean => {
  return sessionStorage.getItem(key) === value
}

/*
 * ログイン終了を示すフラグを削除する処理
 */
export const finishTryingToLogin = (): void => {
  sessionStorage.removeItem(key)
}
