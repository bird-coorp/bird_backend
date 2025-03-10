package br.com.bird.servicebirdad.infrastructure.adapter.controller

import br.com.bird.servicebirdad.application.port.input.TotemUseCase
import br.com.bird.servicebirdad.domain.RequiresProfile
import br.com.bird.servicebirdad.domain.Totem
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/totem")
class TotemController(
    private val totemUseCase: TotemUseCase
) {
    @RequiresProfile("ADMIN")
    @PostMapping
    fun create(@RequestBody totem: Totem) = ResponseEntity.ok().body(totemUseCase.create(totem))

    @GetMapping
    fun get() = ResponseEntity.ok().body(totemUseCase.getAll())
}