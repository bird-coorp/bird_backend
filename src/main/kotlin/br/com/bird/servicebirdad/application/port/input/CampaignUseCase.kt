package br.com.bird.servicebirdad.application.port.input

import br.com.bird.servicebirdad.domain.Campaign
import br.com.bird.servicebirdad.infrastructure.adapter.entity.CampaignEntity

interface CampaignUseCase {
    fun createCampaign(companyId: Long, campaign: Campaign): CampaignEntity
}