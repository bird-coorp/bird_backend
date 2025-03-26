package br.com.bird.servicebirdad.application.service

import br.com.bird.servicebirdad.application.port.input.CampaignUseCase
import br.com.bird.servicebirdad.application.port.output.CampaignFileRepository
import br.com.bird.servicebirdad.application.port.output.CampaignRepositoryPort
import br.com.bird.servicebirdad.application.port.output.CompanyRepositoryPort
import br.com.bird.servicebirdad.application.port.output.TotemRepositoryPort
import br.com.bird.servicebirdad.domain.Campaign
import br.com.bird.servicebirdad.domain.exceptions.BusinessException
import br.com.bird.servicebirdad.infrastructure.adapter.controller.dto.CampaignConfigurationDTO
import br.com.bird.servicebirdad.infrastructure.adapter.controller.dto.CampaignDetailDTO
import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.CampaignEntity
import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.CampaignFileEntity
import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.Status.DENIED
import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.Status.INACTIVE
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.util.Objects.isNull

@Component
class CampaignService(
    @Autowired private val companyRepository: CompanyRepositoryPort,
    @Autowired private val campaignRepository: CampaignRepositoryPort,
    @Autowired private val totemRepositoryPort: TotemRepositoryPort,
    @Autowired private val campaignFileRepository: CampaignFileRepository
) : CampaignUseCase {

    override fun getById(token: String, companyId: Long, id: Long): CampaignDetailDTO {
        val campaign = campaignRepository.findById(id)

        if (campaign.company.id != companyId) {
            throw BusinessException("Essa campanha não pertence a essa empresa")
        }

        return CampaignDetailDTO.fromEntity(campaign)
    }

    override fun getPaged(companyId: Long, pageable: Pageable): Page<CampaignEntity> =
        campaignRepository.findAll(companyId, pageable)

    @Transactional(rollbackOn = [Exception::class])
    override fun createCampaign(companyId: Long, campaign: Campaign, media: MultipartFile): CampaignEntity {

        val company = companyRepository.findById(companyId)

        if (isNull(company)) {
            throw BusinessException("Empresa não encontrada")
        }

        val totems = campaign.totems.map { totemRepositoryPort.findById(it) }.toMutableList()

        val fileId = campaignFileRepository.save(
            CampaignFileEntity(
                id = null,
                content = media.bytes,
                mimeType = media.contentType ?: "video/mp4",
                filename = media.name,
            )
        )

        return campaignRepository.save(
            CampaignEntity(
                id = null,
                adName = campaign.adName,
                objective = campaign.objective,
                budgetType = campaign.budgetType,
                budgetValue = campaign.budgetValue,
                startDate = campaign.startDate,
                endDate = campaign.endDate,
                company = company,
                totems = totems,
                fileId = fileId.id
            )
        )
    }

    @Transactional(rollbackOn = [Exception::class])
    override fun configure(company: Long, campaignId: Long, data: CampaignConfigurationDTO) {
        val campaign = campaignRepository.findById(campaignId)

        if (campaign.status == INACTIVE) {
            throw BusinessException("Campaign $campaignId is inactive")
        }

        if (campaign.status == DENIED) {
            throw BusinessException("Campaign $campaignId is denied")
        }

        if (data.status == DENIED && data.reason.isNullOrBlank()) {
            throw BusinessException("Reason is required when status is denied")
        }

//        if (data.status == Status.DENIED && 1 == 1) {
//            // verificar se usuario é admin
//        }

        campaignRepository.updateStatus(campaignId, data.status)
    }
}