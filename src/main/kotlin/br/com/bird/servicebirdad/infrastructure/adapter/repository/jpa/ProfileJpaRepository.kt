package br.com.bird.servicebirdad.infrastructure.adapter.repository.jpa

import br.com.bird.servicebirdad.infrastructure.adapter.entity.ProfileEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ProfileJpaRepository : JpaRepository<ProfileEntity, Long> {}