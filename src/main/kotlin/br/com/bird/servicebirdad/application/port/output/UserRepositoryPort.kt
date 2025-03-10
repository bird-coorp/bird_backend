package br.com.bird.servicebirdad.application.port.output

import org.springframework.security.core.userdetails.UserDetails

interface UserRepositoryPort {
    fun findByLogin(login: String): UserDetails
}