package br.com.bird.servicebirdad.application.port.output

import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.CampaignFileEntity

interface CampaignFileRepository {
    fun save(campaignFileEntity: CampaignFileEntity): CampaignFileEntity
    fun findById(id: Long): CampaignFileEntity
}