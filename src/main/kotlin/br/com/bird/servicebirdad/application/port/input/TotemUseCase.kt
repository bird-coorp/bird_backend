package br.com.bird.servicebirdad.application.port.input

import br.com.bird.servicebirdad.domain.Totem

interface TotemUseCase {
    fun create(totem: Totem): Totem
    fun getAll(): List<Totem>
}