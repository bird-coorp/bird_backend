package br.com.bird.servicebirdad.infrastructure.adapter.database.repository

import br.com.bird.servicebirdad.application.port.output.CampaignRepositoryPort
import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.CampaignEntity
import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.CampaignProjection
import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.Status
import br.com.bird.servicebirdad.infrastructure.adapter.database.repository.jpa.CampaignJpaRepository
import org.springframework.stereotype.Repository
import org.springframework.data.domain.Pageable

@Repository
class JpaCampingRepository(private val jpaRepository: CampaignJpaRepository) : CampaignRepositoryPort {
    override fun save(campaign: CampaignEntity) = jpaRepository.save(campaign)

    override fun updateStatus(campaignId: Long, status: Status) {
        jpaRepository.updateStatusById(id = campaignId, status = status)
    }

    override fun findAll(companyId: Long, pageable: Pageable) = jpaRepository.findByCompanyId(companyId, pageable)

    override fun findById(id: Long): CampaignEntity = jpaRepository.findById(id).orElseThrow()

    override fun findActiveCampaignsByTotemId(totemId: Long) = jpaRepository.findActiveCampaignsByTotemId(totemId)

    override fun findByStatus(status: Status) = jpaRepository.findByStatus(status)
}