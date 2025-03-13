package br.com.bird.servicebirdad.infrastructure.adapter.controller

import br.com.bird.servicebirdad.application.port.input.TotemUseCase
import br.com.bird.servicebirdad.domain.Totem
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/totem")
class TotemController(
    private val totemUseCase: TotemUseCase
) {

    @PostMapping
    fun create(@RequestBody totem: Totem) = ResponseEntity.ok().body(totemUseCase.create(totem))

    @GetMapping
    fun get() = ResponseEntity.ok().body(totemUseCase.getAll())
}