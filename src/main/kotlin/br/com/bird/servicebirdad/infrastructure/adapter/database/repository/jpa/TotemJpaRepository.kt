package br.com.bird.servicebirdad.infrastructure.adapter.database.repository.jpa

import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.TotemEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TotemJpaRepository : JpaRepository<TotemEntity, Long>