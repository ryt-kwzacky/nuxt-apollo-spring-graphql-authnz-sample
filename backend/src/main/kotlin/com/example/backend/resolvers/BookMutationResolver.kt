package com.example.backend.resolvers

import com.example.backend.Book
import graphql.kickstart.tools.GraphQLMutationResolver
import org.apache.commons.lang3.RandomStringUtils
import org.springframework.stereotype.Component

@Component
class BookMutationResolve: GraphQLMutationResolver {
    fun registerBook(name: String): Book {
        return Book(RandomStringUtils.randomAlphabetic(20), name)
    }
}
