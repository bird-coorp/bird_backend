package br.com.bird.servicebirdad.application.service

import br.com.bird.servicebirdad.application.port.input.TotemUseCase
import br.com.bird.servicebirdad.application.port.output.TotemRepositoryPort
import br.com.bird.servicebirdad.domain.Totem
import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.TotemEntity
import org.springframework.stereotype.Component

@Component
class TotemService(
    private val totemRepository: TotemRepositoryPort,
) : TotemUseCase {
    override fun create(totem: Totem) = totemRepository.create(
        totem = totem.toEntity()
    ).toDomain()

    override fun getAll(): List<Totem> = totemRepository.findAll().map { it.toDomain() }

    private fun Totem.toEntity() = TotemEntity(
        id = null,
        name = name,
        longitude = longitude,
        latitude = latitude,
        enabled = enabled
    )

    private fun TotemEntity.toDomain() = Totem(
        id = id,
        name = name,
        longitude = longitude,
        latitude = latitude,
        enabled = enabled
    )
}