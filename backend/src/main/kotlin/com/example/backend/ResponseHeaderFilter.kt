package com.example.backend

import com.example.backend.config.ApplicationProperties
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
class ResponseHeaderFilter: Filter {
    companion object {
        private val LOGGER = LoggerFactory.getLogger(this::class.java)

        private val ALLOWED_METHODS = listOf("GET", "POST", "OPTIONS")

        private val STANDARD_ALLOWED_HEADERS = listOf(
            "Accept",
            "Content-Type",
            "Referer",
            "User-Agent",
            "Origin",
        )

        private val ALLOWED_HEADERS = listOf(
            *STANDARD_ALLOWED_HEADERS.toTypedArray(),
            "Authorization"
        )
    }

    @Autowired
    val applicationProperties: ApplicationProperties = ApplicationProperties()

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(req: ServletRequest, res: ServletResponse, chain: FilterChain) {
        val request = req as HttpServletRequest
        val response = res as HttpServletResponse
        val allowedMethods = ALLOWED_METHODS.joinToString(separator = ",")
        val allowedHeaders = ALLOWED_HEADERS.joinToString(separator = ",")
        val allowedDomain = getAllowOrigin(currentOrigin = request.getHeader("Origin"))

        response.setHeader("Access-Control-Allow-Methods", allowedMethods)
        if (allowedDomain != null) {
            response.setHeader("Access-Control-Allow-Origin", allowedDomain)
        }
        response.setHeader("Access-Control-Allow-Credentials", "false")
        response.setHeader("Access-Control-Allow-Headers", allowedHeaders)
        response.setHeader("Access-Control-Max-Age", "600")

        chain.doFilter(req, res)
    }

    private fun getAllowOrigin(currentOrigin: String?): String? =
        // カンマ繋ぎで設定されているドメインを配列にパース
        applicationProperties.security.allowedFrontendDomain.split(",").map { it.trim() }.let { allowedDomains ->
            // 設定されているドメインが１つだけの場合はそのままそのドメインを許可したオリジンとして返す。
            if (allowedDomains.count() == 1)
                allowedDomains[0]
            else
                allowedDomains.find { it == currentOrigin }
        }
}
