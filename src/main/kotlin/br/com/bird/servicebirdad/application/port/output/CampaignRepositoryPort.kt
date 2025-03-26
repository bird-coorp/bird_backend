package br.com.bird.servicebirdad.application.port.output

import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.CampaignEntity
import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.CampaignProjection
import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.Status
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CampaignRepositoryPort {
    fun save(campaign: CampaignEntity): CampaignEntity
    fun updateStatus(campaignId: Long, status: Status)
    fun findAll(companyId: Long, pageable: Pageable): Page<CampaignEntity>
    fun findById(id: Long): CampaignEntity
    fun findActiveCampaignsByTotemId(totemId: Long): List<CampaignProjection>
    fun findByStatus(status: Status): List<CampaignEntity>
}