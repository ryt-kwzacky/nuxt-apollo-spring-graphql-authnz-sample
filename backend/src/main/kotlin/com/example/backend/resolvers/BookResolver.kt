package com.example.backend.resolvers

import com.example.backend.Book
import graphql.kickstart.tools.GraphQLQueryResolver
import org.apache.commons.lang3.RandomStringUtils
import org.springframework.stereotype.Component

@Component
class BookResolver: GraphQLQueryResolver {
    fun searchBookById(bookId: String): Book {
        return Book(bookId, RandomStringUtils.randomAlphabetic(20))
    }
}
