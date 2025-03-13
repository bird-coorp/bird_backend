package br.com.bird.servicebirdad.application.port.input

import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.CampaignFileEntity

interface FileUseCase {
    fun getById(id: Long): CampaignFileEntity
}