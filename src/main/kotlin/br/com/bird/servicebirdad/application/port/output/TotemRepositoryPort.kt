package br.com.bird.servicebirdad.application.port.output

import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.TotemEntity

interface TotemRepositoryPort {
    fun create(totem: TotemEntity): TotemEntity
    fun findAll(): List<TotemEntity>
    fun findById(it: Long): TotemEntity
}