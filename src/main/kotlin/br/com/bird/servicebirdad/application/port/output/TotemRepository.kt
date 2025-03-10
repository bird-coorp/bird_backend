package br.com.bird.servicebirdad.application.port.output

import br.com.bird.servicebirdad.infrastructure.adapter.entity.TotemEntity

interface TotemRepository {
    fun create(totem: TotemEntity): TotemEntity
    fun findAll(): List<TotemEntity>
}