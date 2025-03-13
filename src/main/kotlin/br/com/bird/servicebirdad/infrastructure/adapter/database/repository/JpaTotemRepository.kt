package br.com.bird.servicebirdad.infrastructure.adapter.database.repository

import br.com.bird.servicebirdad.application.port.output.TotemRepositoryPort
import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.TotemEntity
import br.com.bird.servicebirdad.infrastructure.adapter.database.repository.jpa.TotemJpaRepository
import org.springframework.stereotype.Repository

@Repository
class JpaTotemRepository(
    private val totemJpaRepository: TotemJpaRepository
) : TotemRepositoryPort {
    override fun create(totem: TotemEntity) = totemJpaRepository.save(totem)
    override fun findAll(): List<TotemEntity> = totemJpaRepository.findAll()
    override fun findById(it: Long): TotemEntity = totemJpaRepository.findById(it).orElseThrow()
}