package br.com.bird.servicebirdad.infrastructure.adapter.database.repository

import br.com.bird.servicebirdad.application.port.output.CampaignReasonHistoricPort
import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.CampaignReasonHistoricEntity
import br.com.bird.servicebirdad.infrastructure.adapter.database.repository.jpa.CampaignReasonHistoricJpaRepository
import org.springframework.stereotype.Component

@Component
class JpaCampaignReasonHistoricRepository(
    private val repository: CampaignReasonHistoricJpaRepository
) : CampaignReasonHistoricPort {
    override fun save(it: CampaignReasonHistoricEntity) = repository.save(it)
}