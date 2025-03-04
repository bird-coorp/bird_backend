package br.com.bird.servicebirdad.application.port.output

import br.com.bird.servicebirdad.infrastructure.adapter.entity.CampaignEntity

interface CampaignRepository {
    fun save(campaign: CampaignEntity): CampaignEntity
}