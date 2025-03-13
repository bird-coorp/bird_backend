package br.com.bird.servicebirdad.infrastructure.adapter.database.repository.jpa

import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.CampaignFileEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CampaignFileJpaRepository : JpaRepository<CampaignFileEntity, Long>