package com.example.backend

import graphql.kickstart.tools.GraphQLQueryResolver
import org.springframework.stereotype.Component

@Component
class BookResolver: GraphQLQueryResolver {
    fun bookById(bookId: String): Book {
        return Book("testId", "nameName")
    }
}
