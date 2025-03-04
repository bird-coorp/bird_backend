package br.com.bird.servicebirdad.infrastructure.adapter.repository.jpa

import br.com.bird.servicebirdad.infrastructure.adapter.entity.CampaignEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CampaignJpaRepository : JpaRepository<CampaignEntity, Long> {
}