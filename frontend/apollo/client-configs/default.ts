import { ApolloLink } from 'apollo-link'
import { HttpLink } from 'apollo-link-http'
import { InMemoryCache } from 'apollo-cache-inmemory'

export default () => {
  const httpLink = new HttpLink({ uri: 'http://localhost:8080/graphql' })

  // auth token
  // これをユーザーのtoken入れて送れるようにする
  const token = 'xxxxxxxxxxxxx'
  console.log('---------------------------------')
  console.log('apollo test')

  // middleware
  const middlewareLink = new ApolloLink((operation, forward) => {
    operation.setContext({
      headers: { Authorization: `Bearer ${token}` },
    })
    return forward(operation)
  })
  const link = middlewareLink.concat(httpLink)
  return {
    link,
    cache: new InMemoryCache(),
  }
}
