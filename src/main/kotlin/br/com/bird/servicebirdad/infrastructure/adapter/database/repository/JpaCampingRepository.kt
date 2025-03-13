package br.com.bird.servicebirdad.infrastructure.adapter.database.repository

import br.com.bird.servicebirdad.application.port.output.CampaignRepository
import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.CampaignEntity
import br.com.bird.servicebirdad.infrastructure.adapter.database.repository.jpa.CampaignJpaRepository
import org.springframework.stereotype.Repository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

@Repository
class JpaCampingRepository(private val jpaRepository: CampaignJpaRepository) : CampaignRepository {
    override fun save(campaign: CampaignEntity) = jpaRepository.save(campaign)

    override fun findAll(companyId: Long, pageable: Pageable) = jpaRepository.findByCompanyId(companyId, pageable)

    override fun findById(id: Long): CampaignEntity = jpaRepository.findById(id).orElseThrow()
}