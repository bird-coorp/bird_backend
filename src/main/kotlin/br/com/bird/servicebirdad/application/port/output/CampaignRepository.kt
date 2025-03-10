package br.com.bird.servicebirdad.application.port.output

import br.com.bird.servicebirdad.infrastructure.adapter.entity.CampaignEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CampaignRepository {
    fun save(campaign: CampaignEntity): CampaignEntity
    fun findAll(companyId: Long, pageable: Pageable): Page<CampaignEntity>
}