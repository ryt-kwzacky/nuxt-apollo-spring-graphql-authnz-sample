import { auth } from '~/plugins/firebase'

export default function ({ redirect }) {
  const authUser: firebase.User | null = auth.currentUser

  console.log('----- middleware -----')
  console.log(auth.currentUser)
  if (authUser === null) {
    console.log('----- redirect -----')
    redirect('/')
    return
  }
  console.log('----- pass middleware -----')
}
