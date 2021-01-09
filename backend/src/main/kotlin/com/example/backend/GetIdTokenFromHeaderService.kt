package com.example.backend

import javax.servlet.http.HttpServletRequest

interface GetIdTokenFromHeaderService {
    /**
     * @return ID Token contained in headers
     */
    fun execute(request: HttpServletRequest): IdToken?

    data class IdToken(
        val value: String
    )
}
