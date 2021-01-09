package com.example.backend

import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest

@Component
class GetFirebaseIdTokenFromHeaderService: GetIdTokenFromHeaderService {
    companion object {
        private const val TOKEN_PREFIX = "Bearer "
    }

    override fun execute(request: HttpServletRequest): GetIdTokenFromHeaderService.IdToken? {
        val bearerToken: String? = request.getHeader(SecurityHttpRequestHeader.AUTHORIZATION)

        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            val value = bearerToken.substring(TOKEN_PREFIX.length, bearerToken.length)
            return GetIdTokenFromHeaderService.IdToken(value = value)
        }
        return null
    }
}
