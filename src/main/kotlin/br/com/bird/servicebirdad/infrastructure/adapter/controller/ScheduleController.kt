package br.com.bird.servicebirdad.infrastructure.adapter.controller

import br.com.bird.servicebirdad.application.port.input.ScheduleUseCase
import br.com.bird.servicebirdad.infrastructure.adapter.controller.dto.ScheduleResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/schedule")
class ScheduleController(
    private val useCase: ScheduleUseCase
) {

    @GetMapping("/{totemId}")
    fun getSchedule(@PathVariable totemId: Long): ResponseEntity<ScheduleResponse> {
        return ResponseEntity.ok(useCase.getSchedule(totemId))
    }
}