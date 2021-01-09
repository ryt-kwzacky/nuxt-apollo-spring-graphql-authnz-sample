export const state = () => ({
  test: 'test-init',
})

export const mutations = {
  changeTest(state) {
    state.test = 'test-changed'
  },
}
