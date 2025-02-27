package br.com.bird.servicebirdad.infrastructure.config

import br.com.bird.servicebirdad.infrastructure.adapter.entity.ProfileEntity
import br.com.bird.servicebirdad.infrastructure.adapter.repository.jpa.ProfileJpaRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ProfilesBeanConfig(
    private val profileJpaRepository: ProfileJpaRepository
) {

    @Bean
    fun getAllProfiles(): MutableList<ProfileEntity> = profileJpaRepository.findAll()
}