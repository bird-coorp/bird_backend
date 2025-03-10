package br.com.bird.servicebirdad.application.port.input

import br.com.bird.servicebirdad.domain.Campaign
import br.com.bird.servicebirdad.infrastructure.adapter.entity.CampaignEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CampaignUseCase {
    fun getPaged(companyId: Long, pageable: Pageable): Page<CampaignEntity>
    fun createCampaign(companyId: Long, campaign: Campaign): CampaignEntity
}