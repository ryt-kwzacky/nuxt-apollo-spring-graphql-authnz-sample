package com.example.backend

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JsonWebTokenParseFilter: OncePerRequestFilter() {
    companion object {
        private val LOGGER = LoggerFactory.getLogger(this::class.java)
    }

    @Autowired
    private lateinit var getIdTokenFromHeaderService: GetIdTokenFromHeaderService

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        LOGGER.info("Start Authentication.")

        val idToken = getIdTokenFromHeaderService.execute(request)
        LOGGER.info("--------------------------------")
        LOGGER.info(idToken.toString())

        filterChain.doFilter(request, response)
    }
}
