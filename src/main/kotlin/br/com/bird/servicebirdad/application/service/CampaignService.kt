package br.com.bird.servicebirdad.application.service

import br.com.bird.servicebirdad.application.port.input.CampaignUseCase
import br.com.bird.servicebirdad.application.port.output.*
import br.com.bird.servicebirdad.domain.Campaign
import br.com.bird.servicebirdad.domain.exceptions.BusinessException
import br.com.bird.servicebirdad.infrastructure.adapter.controller.dto.CampaignConfigurationDTO
import br.com.bird.servicebirdad.infrastructure.adapter.controller.dto.CampaignDetailDTO
import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.*
import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.Status.DENIED
import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.Status.INACTIVE
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime
import java.util.Objects.isNull

@Component
class CampaignService(
    @Autowired private val minioService: MinioService,
    @Autowired private val companyRepository: CompanyRepositoryPort,
    @Autowired private val campaignRepository: CampaignRepositoryPort,
    @Autowired private val totemRepositoryPort: TotemRepositoryPort,
    @Autowired private val campaignFileRepository: CampaignFilePort,
    @Autowired private val historicRepositoryPort: HistoricRepositoryPort,
    @Autowired private val campaignReasonHistoricPort: CampaignReasonHistoricPort,
    @Autowired private val tokenService: TokenService,
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

        val fileName = minioService.upload(file = media)

        val fileId = campaignFileRepository.save(
            CampaignFileEntity(
                id = null,
                mimeType = media.contentType ?: "video/mp4",
                filename = fileName
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
    override fun configure(
        company: Long,
        campaignId: Long,
        data: CampaignConfigurationDTO,
        token: String
    ) {
        val campaign = campaignRepository.findById(campaignId)
        val isAdmin = tokenService.havingClaim(token, "ADMIN")
        var reason: CampaignReasonHistoricEntity? = null

        if (campaign.status == INACTIVE) {
            throw BusinessException("Campaign $campaignId is inactive")
        }

        if (campaign.status == DENIED) {
            throw BusinessException("Campaign $campaignId is denied")
        }

        if (data.status == DENIED && data.reason.isNullOrBlank()) {
            throw BusinessException("Reason is required when status is denied")
        } else {
            reason = campaignReasonHistoricPort.save(
                CampaignReasonHistoricEntity(
                    null,
                    reason = data.status.name
                )
            )
        }

        if (data.status == DENIED && !isAdmin) {
            throw BusinessException("You do not have permission to perform this action")
        }

        val historic = historicRepositoryPort.save(
            HistoricEntity(
                id = null,
                since = campaign.status,
                until = data.status,
                createdAt = LocalDateTime.now(),
                origin = Origin.WEB
            )
        )

        campaign.historic.add(historic)
        campaign.status = data.status
        campaign.campaignReasonHistoric.add(reason)

        campaignRepository.save(campaign)
    }
}