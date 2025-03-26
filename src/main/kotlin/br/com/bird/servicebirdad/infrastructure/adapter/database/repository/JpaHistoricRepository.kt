package br.com.bird.servicebirdad.infrastructure.adapter.database.repository

import br.com.bird.servicebirdad.application.port.output.HistoricRepositoryPort
import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.HistoricEntity
import br.com.bird.servicebirdad.infrastructure.adapter.database.repository.jpa.HistoricJpaRepository
import org.springframework.stereotype.Component

@Component
class JpaHistoricRepository(
    private val repository: HistoricJpaRepository,
) : HistoricRepositoryPort {
    override fun save(historic: HistoricEntity): HistoricEntity = repository.save(historic)
}