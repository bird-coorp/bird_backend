package br.com.bird.servicebirdad.infrastructure.adapter.database.repository.jpa

import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.CampaignReasonHistoricEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CampaignReasonHistoricJpaRepository : JpaRepository<CampaignReasonHistoricEntity, Long>