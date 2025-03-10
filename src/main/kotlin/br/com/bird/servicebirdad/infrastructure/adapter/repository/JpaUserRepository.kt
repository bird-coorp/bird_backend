package br.com.bird.servicebirdad.infrastructure.adapter.repository

import br.com.bird.servicebirdad.application.port.output.UserRepositoryPort
import br.com.bird.servicebirdad.infrastructure.adapter.entity.ProfileEntity
import br.com.bird.servicebirdad.infrastructure.adapter.entity.UserEntity
import br.com.bird.servicebirdad.infrastructure.adapter.repository.jpa.ProfileJpaRepository
import br.com.bird.servicebirdad.infrastructure.adapter.repository.jpa.UserJpaRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Repository

@Repository
class JpaUserRepository(
    private val userJpaRepository: UserJpaRepository
) : UserRepositoryPort {
    override fun findByLogin(login: String): UserDetails {
        return userJpaRepository.findByEmail(login)
    }
}