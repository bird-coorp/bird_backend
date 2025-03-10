package br.com.bird.servicebirdad.infrastructure.adapter.controller.interceptor

import br.com.bird.servicebirdad.application.service.TokenClaims
import br.com.bird.servicebirdad.application.service.TokenService
import br.com.bird.servicebirdad.domain.RequiresProfile
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor

@Component
class ProfileInterceptor(
    private val tokenService: TokenService
) : HandlerInterceptor {

    val mapper = jacksonObjectMapper()

    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean {
        if (handler is HandlerMethod) {
            val requiresProfile = handler.getMethodAnnotation(RequiresProfile::class.java)

            if (requiresProfile != null) {
                val token = request.getHeader("Authorization")?.replace("Bearer ", "") ?: run {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token não encontrado")
                    return false
                }

                val userProfiles = extractProfilesFromToken(token)

                if (!userProfiles.contains(requiresProfile.profile)) {
                    response.sendError(
                        HttpServletResponse.SC_FORBIDDEN,
                        "Acesso negado para o perfil: ${requiresProfile.profile}"
                    )
                    return false
                }
            }
        }
        return true
    }

    private fun extractProfilesFromToken(token: String): List<String> {
        return try {
            val data = tokenService.getPayload(token.replace("Bearer ", ""))
            val mapped = mapper.readValue(data, TokenClaims::class.java)
            mapped.role
        } catch (e: Exception) {
            emptyList()
        }
    }
}
