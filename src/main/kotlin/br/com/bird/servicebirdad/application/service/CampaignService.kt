package br.com.bird.servicebirdad.application.service

import br.com.bird.servicebirdad.application.port.input.CampaignUseCase
import br.com.bird.servicebirdad.application.port.output.CampaignRepository
import br.com.bird.servicebirdad.application.port.output.CompanyRepositoryPort
import br.com.bird.servicebirdad.domain.Campaign
import br.com.bird.servicebirdad.domain.exceptions.BusinessException
import br.com.bird.servicebirdad.infrastructure.adapter.entity.CampaignEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import java.util.Objects.isNull

@Component
class CampaignService(
    @Autowired private val companyRepository: CompanyRepositoryPort,
    @Autowired private val campaignRepository: CampaignRepository
) : CampaignUseCase {

    override fun getPaged(companyId: Long, pageable: Pageable): Page<CampaignEntity> {
        return campaignRepository.findAll(companyId, pageable)
    }

    override fun createCampaign(companyId: Long, campaign: Campaign): CampaignEntity {
        val company = companyRepository.findById(companyId)

        if (isNull(company)) {
            throw BusinessException("Empresa não encontrada")
        }

        return campaignRepository.save(
            CampaignEntity(
                id = null,
                adName = campaign.adName,
                objective = campaign.objective,
                budgetType = campaign.budgetType,
                budgetValue = campaign.budgetValue,
                startDate = campaign.startDate,
                endDate = campaign.endDate,
                company = company
            )
        )

    }
}