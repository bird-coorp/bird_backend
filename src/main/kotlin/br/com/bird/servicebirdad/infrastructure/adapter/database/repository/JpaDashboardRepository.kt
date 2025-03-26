package br.com.bird.servicebirdad.infrastructure.adapter.database.repository

import br.com.bird.servicebirdad.application.port.output.DashboardRepositoryPort
import br.com.bird.servicebirdad.infrastructure.adapter.database.repository.jpa.CampaignJpaRepository
import org.springframework.stereotype.Repository

@Repository
class JpaDashboardRepository(
    private val campaignJpaRepository: CampaignJpaRepository
) : DashboardRepositoryPort {
    override fun getData(): Number = campaignJpaRepository.countActiveCampaigns()
    override fun getDataByCompany(company: Long): Number = campaignJpaRepository.countActiveCampaignsByCompanyId(company)
}