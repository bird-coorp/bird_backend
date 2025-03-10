package br.com.bird.servicebirdad.application.service

import br.com.bird.servicebirdad.application.port.output.UserRepositoryPort
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AuthorizationService(
    private val userRepositoryPort: UserRepositoryPort
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        return userRepositoryPort.findByLogin(username)
    }
}