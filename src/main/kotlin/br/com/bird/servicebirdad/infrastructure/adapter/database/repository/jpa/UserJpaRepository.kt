package br.com.bird.servicebirdad.infrastructure.adapter.database.repository.jpa

import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.security.core.userdetails.UserDetails

interface UserJpaRepository : JpaRepository<UserEntity, Long> {
    @Query("SELECT u FROM UserEntity u WHERE u.email = :email")
    fun findByEmail(@Param("email") email: String): UserDetails
}