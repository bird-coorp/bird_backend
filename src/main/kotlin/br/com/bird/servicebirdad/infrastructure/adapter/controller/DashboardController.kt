package br.com.bird.servicebirdad.infrastructure.adapter.controller

import br.com.bird.servicebirdad.application.port.input.DashboardUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/dashboard")
class DashboardController(
    private val dashboardUseCase: DashboardUseCase
) {

    @GetMapping
    fun get(
        @RequestHeader("Authorization") token: String,
    ): ResponseEntity<Any> {
        val totalActive = dashboardUseCase.getDashboardData(token)
        return ResponseEntity.ok(mapOf("totalActive" to totalActive))
    }
}