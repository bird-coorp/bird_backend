package br.com.bird.servicebirdad.infrastructure.adapter.controller

import br.com.bird.servicebirdad.application.port.input.CampaignUseCase
import br.com.bird.servicebirdad.domain.Campaign
import br.com.bird.servicebirdad.infrastructure.adapter.controller.dto.*
import org.springframework.data.domain.PageRequest
import org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.net.URI
import java.time.ZoneId

@RestController
@RequestMapping("/campaigns")
class CampaignController(
    private val campaignUseCase: CampaignUseCase,
) {
    @GetMapping
    fun getPaged(
        @RequestHeader("companyId") companyId: Long,
        @RequestParam("page", defaultValue = "0") page: Int,
        @RequestParam("size", defaultValue = "10") size: Int
    ): ResponseEntity<CampaignResponseDto> {
        val pageable = PageRequest.of(page, size)
        val response = campaignUseCase.getPaged(companyId, pageable)
            .map { CampaignResultDto.fromEntity(it, companyId) }
        val campaignResponse =
            CampaignResponseDto(
                content = response.content,
                page = pageable.pageNumber,
                size = pageable.pageSize
            )

        return ResponseEntity.ok(campaignResponse)
    }


    @GetMapping("/{id}")
    fun getById(
        @RequestHeader("Authorization") token: String,
        @RequestHeader("companyId") companyId: Long,
        @PathVariable id: Long,
    ): ResponseEntity<CampaignDetailDTO> {
        return ResponseEntity.ok(
            campaignUseCase.getById(token, companyId, id)
        )
    }

    @PostMapping(consumes = [MULTIPART_FORM_DATA_VALUE])
    fun create(
        @RequestPart("file") file: MultipartFile,
        @RequestHeader("companyId") companyId: Long,
        @RequestPart("campaignDto") campaignDto: CampaignDto,
    ): ResponseEntity<Void> {
        campaignUseCase.createCampaign(
            media = file,
            companyId = companyId,
            campaign = Campaign(
                id = null,
                adName = campaignDto.budget.name,
                objective = campaignDto.objective,
                budgetType = campaignDto.budget.type,
                budgetValue = campaignDto.budget.amount,
                startDate = campaignDto.budget.scheduleStart.atZone(ZoneId.systemDefault()).toLocalDate(),
                endDate = campaignDto.budget.scheduleEnd.atZone(ZoneId.systemDefault()).toLocalDate(),
                companyId = companyId,
                totems = campaignDto.localization.map { it.id }
            ),
        )

        return ResponseEntity.created(URI.create("/campaigns")).build()
    }

    @PutMapping("/{id}")
    fun configure(
        @RequestHeader("Authorization") token: String,
        @RequestHeader("companyId") companyId: Long,
        @PathVariable id: Long,
        @RequestBody campaignConfigurationDTO: CampaignConfigurationDTO
    ): ResponseEntity<Void> {
        campaignUseCase.configure(
            company = companyId,
            campaignId = id,
            data = campaignConfigurationDTO
        )
        return ResponseEntity.noContent().build<Void>()
    }
}