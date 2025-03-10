package br.com.bird.servicebirdad.infrastructure.adapter.repository.jpa

import br.com.bird.servicebirdad.infrastructure.adapter.entity.TotemEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TotemJpaRepository : JpaRepository<TotemEntity, Long>