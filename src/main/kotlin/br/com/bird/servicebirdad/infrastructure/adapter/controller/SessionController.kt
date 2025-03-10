package br.com.bird.servicebirdad.infrastructure.adapter.controller

import br.com.bird.servicebirdad.application.service.TokenService
import br.com.bird.servicebirdad.infrastructure.adapter.entity.UserEntity
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/sessions")
class SessionController(
    private val authenticationManager: AuthenticationManager,
    private val tokenService: TokenService,
) {

    @PostMapping
    fun session(@RequestBody request: SessionRequest): ResponseEntity<SessionResponse> {
        val usernamePassword = UsernamePasswordAuthenticationToken(request.login, request.password)
        val authentication = authenticationManager.authenticate(usernamePassword)
        val token = tokenService.generate(
            userEntity = authentication.principal as UserEntity
        )

        return ResponseEntity
            .ok()
            .header("Authorization", token)
            .body(SessionResponse(token))
    }

    @GetMapping
    fun validate(@RequestHeader("Authorization") token: String): ResponseEntity<Void> {
        tokenService.validate(token)
        return ResponseEntity.ok().build()
    }
}

data class SessionResponse(
    val token: String,
)

data class SessionRequest(
    val login: String,
    val password: String,
)