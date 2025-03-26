package br.com.bird.servicebirdad.application.port.output

import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.ScheduleEntity

interface ScheduleRepositoryPort {
    fun findScheduleByTotemId(totemId: Long): ScheduleEntity?
}