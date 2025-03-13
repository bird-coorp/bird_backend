package br.com.bird.servicebirdad.infrastructure.adapter.database.repository

import br.com.bird.servicebirdad.application.port.output.CampaignFileRepository
import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.CampaignFileEntity
import br.com.bird.servicebirdad.infrastructure.adapter.database.repository.jpa.CampaignFileJpaRepository
import org.springframework.stereotype.Repository

@Repository
class JpaCampaignRepository(
    private val campaignFileJpaRepository: CampaignFileJpaRepository,
) : CampaignFileRepository {
    override fun save(campaignFileEntity: CampaignFileEntity) = campaignFileJpaRepository.save(campaignFileEntity)

    override fun findById(id: Long): CampaignFileEntity = campaignFileJpaRepository.findById(id).orElseThrow()
}