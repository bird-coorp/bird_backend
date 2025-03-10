package br.com.bird.servicebirdad.infrastructure.adapter.repository

import br.com.bird.servicebirdad.application.port.output.TotemRepository
import br.com.bird.servicebirdad.infrastructure.adapter.entity.TotemEntity
import br.com.bird.servicebirdad.infrastructure.adapter.repository.jpa.TotemJpaRepository
import org.springframework.stereotype.Repository

@Repository
class JpaTotemRepository(
    private val totemJpaRepository: TotemJpaRepository
) : TotemRepository {
    override fun create(totem: TotemEntity) = totemJpaRepository.save(totem)
    override fun findAll(): List<TotemEntity> = totemJpaRepository.findAll()
}