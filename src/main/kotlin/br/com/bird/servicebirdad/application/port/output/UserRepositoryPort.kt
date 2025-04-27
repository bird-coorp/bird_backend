package br.com.bird.servicebirdad.application.port.output

import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.UserEntity
import org.springframework.security.core.userdetails.UserDetails
import java.util.Optional

interface UserRepositoryPort {
    fun findByLogin(login: String): UserDetails
    fun findById(id: Long): Optional<UserEntity>
}