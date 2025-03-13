package br.com.bird.servicebirdad.infrastructure.adapter.database.repository.jpa

import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.ProfileEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ProfileJpaRepository : JpaRepository<ProfileEntity, Long> {}