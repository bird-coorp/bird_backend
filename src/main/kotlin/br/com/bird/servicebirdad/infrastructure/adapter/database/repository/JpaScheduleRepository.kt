package br.com.bird.servicebirdad.infrastructure.adapter.database.repository

import br.com.bird.servicebirdad.application.port.output.ScheduleRepositoryPort
import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.ScheduleEntity
import br.com.bird.servicebirdad.infrastructure.adapter.database.repository.jpa.ScheduleJpaRepository
import org.springframework.stereotype.Repository

@Repository
class JpaScheduleRepository(
    private val jpaRepository: ScheduleJpaRepository
) : ScheduleRepositoryPort {
    override fun findScheduleByTotemId(totemId: Long): ScheduleEntity? {
        return jpaRepository.findScheduleByTotemId(totemId)
    }
}