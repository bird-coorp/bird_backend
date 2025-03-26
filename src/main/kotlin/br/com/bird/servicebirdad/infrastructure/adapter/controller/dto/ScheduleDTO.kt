package br.com.bird.servicebirdad.infrastructure.adapter.controller.dto

data class ScheduleResponse(
    val schedule: MutableList<ScheduleDTO>,
    val mediasIds: MutableSet<Long> = mutableSetOf()
)

data class ScheduleDTO(
    val time: String,
    val fileId: Long
)