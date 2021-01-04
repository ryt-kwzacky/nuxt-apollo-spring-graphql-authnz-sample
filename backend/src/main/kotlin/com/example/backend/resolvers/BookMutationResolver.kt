package com.example.backend.resolvers

import com.example.backend.Book
import com.example.backend.db.generatedJooqCode.tables.JBooks
import graphql.kickstart.tools.GraphQLMutationResolver
import graphql.kickstart.tools.GraphQLQueryResolver
import org.apache.commons.lang3.RandomStringUtils
import org.jooq.DSLContext
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class BookMutationResolve(
    @Autowired private val dslContext: DSLContext
): GraphQLMutationResolver {
    companion object {
        private val BOOKS = JBooks.BOOKS
    }

    fun registerBook(name: String): Book {
        dslContext
            .insertInto(BOOKS)
            .set(BOOKS.NAME, name)
            .execute()
        return Book(999, name) // TODO: GraphQLで返り値付与しない方法調べる
    }
}
