package br.com.bird.servicebirdad.infrastructure.adapter.database.repository

import br.com.bird.servicebirdad.application.port.output.UserRepositoryPort
import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.ProfileEntity
import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.UserEntity
import br.com.bird.servicebirdad.infrastructure.adapter.database.repository.jpa.ProfileJpaRepository
import br.com.bird.servicebirdad.infrastructure.adapter.database.repository.jpa.UserJpaRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Repository

@Repository
class JpaUserRepository(
    private val userJpaRepository: UserJpaRepository
) : UserRepositoryPort {
    override fun findByLogin(login: String): UserDetails {
        return userJpaRepository.findByEmail(login)
    }

    override fun findById(id: Long) =
        userJpaRepository.findById(id)
}