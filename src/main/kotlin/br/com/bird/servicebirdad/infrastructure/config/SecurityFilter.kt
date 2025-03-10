package br.com.bird.servicebirdad.infrastructure.config

import br.com.bird.servicebirdad.application.port.output.UserRepositoryPort
import br.com.bird.servicebirdad.application.service.TokenService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class SecurityFilter(
    private val tokenService: TokenService,
    private val profileRepositoryPort: UserRepositoryPort,
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = recoverToken(request)

        if (token != null) {
            val login = tokenService.validate(token)
            val user = profileRepositoryPort.findByLogin(login)

            val authentication = UsernamePasswordAuthenticationToken(
                user, null, user.authorities
            )

            SecurityContextHolder.getContext().authentication = authentication
        }

        filterChain.doFilter(request, response)
    }

    private fun recoverToken(request: HttpServletRequest): String? {
        val authHeader = request.getHeader("Authorization") ?: return null

        return authHeader.replace("Bearer ", "")
    }
}