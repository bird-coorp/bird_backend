package br.com.bird.servicebirdad.application.port.output

import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.HistoricEntity

interface HistoricRepositoryPort {
    fun save(historic: HistoricEntity): HistoricEntity
}