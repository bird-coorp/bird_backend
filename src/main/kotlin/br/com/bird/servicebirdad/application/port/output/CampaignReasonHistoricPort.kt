package br.com.bird.servicebirdad.application.port.output

import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.CampaignReasonHistoricEntity

interface CampaignReasonHistoricPort {
    fun save(it: CampaignReasonHistoricEntity): CampaignReasonHistoricEntity
}