package br.com.bird.servicebirdad.infrastructure.adapter.repository

import br.com.bird.servicebirdad.application.port.output.CampaignRepository
import br.com.bird.servicebirdad.infrastructure.adapter.entity.CampaignEntity
import br.com.bird.servicebirdad.infrastructure.adapter.repository.jpa.CampaignJpaRepository
import org.springframework.stereotype.Repository

@Repository
class JpaCampingRepository(private val jpaRepository: CampaignJpaRepository) : CampaignRepository {
    override fun save(campaign: CampaignEntity): CampaignEntity {
        return jpaRepository.save(campaign)
    }
}