package br.com.bird.servicebirdad.application.port.input

import br.com.bird.servicebirdad.infrastructure.adapter.controller.dto.ScheduleResponse

interface ScheduleUseCase {
    fun getSchedule(totemId: Long): ScheduleResponse
}