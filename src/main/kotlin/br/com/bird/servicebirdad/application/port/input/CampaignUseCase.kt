package br.com.bird.servicebirdad.application.port.input

import br.com.bird.servicebirdad.domain.Campaign
import br.com.bird.servicebirdad.infrastructure.adapter.controller.dto.CampaignConfigurationDTO
import br.com.bird.servicebirdad.infrastructure.adapter.controller.dto.CampaignDetailDTO
import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.CampaignEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.multipart.MultipartFile

interface CampaignUseCase {
    fun getById(token: String, companyId: Long, id: Long): CampaignDetailDTO
    fun getPaged(companyId: Long, pageable: Pageable): Page<CampaignEntity>
    fun createCampaign(companyId: Long, campaign: Campaign, media: MultipartFile): CampaignEntity
    fun configure(company: Long, campaign: Long, data: CampaignConfigurationDTO)
}