package com.example.backend.resolvers

import com.example.backend.Book
import com.example.backend.db.generatedJooqCode.tables.JBooks
import graphql.kickstart.tools.GraphQLQueryResolver
import org.apache.commons.lang3.RandomStringUtils
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class BookResolver(
    @Autowired private val dslContext: DSLContext
): GraphQLQueryResolver {
    companion object {
        private val BOOKS = JBooks.BOOKS
    }

    fun searchBookById(id: Int): Book {
        val result =
            dslContext
                .select()
                .from(BOOKS)
                .where(BOOKS.ID.eq(id))
                .fetchOne()

        return Book(result.getValue(BOOKS.ID), result.getValue(BOOKS.NAME))
    }
}
