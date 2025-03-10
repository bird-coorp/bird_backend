package br.com.bird.servicebirdad.infrastructure.adapter.repository

import br.com.bird.servicebirdad.application.port.output.CampaignRepository
import br.com.bird.servicebirdad.infrastructure.adapter.entity.CampaignEntity
import br.com.bird.servicebirdad.infrastructure.adapter.repository.jpa.CampaignJpaRepository
import org.springframework.stereotype.Repository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable


@Repository
class JpaCampingRepository(private val jpaRepository: CampaignJpaRepository) : CampaignRepository {
    override fun save(campaign: CampaignEntity): CampaignEntity {
        return jpaRepository.save(campaign)
    }

    override fun findAll(companyId: Long, pageable: Pageable): Page<CampaignEntity> {
        return jpaRepository.findByCompanyId(companyId, pageable)
    }
}