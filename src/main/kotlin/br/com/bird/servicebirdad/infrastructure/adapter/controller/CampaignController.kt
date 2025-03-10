package br.com.bird.servicebirdad.infrastructure.adapter.controller

import br.com.bird.servicebirdad.application.port.input.CampaignUseCase
import br.com.bird.servicebirdad.domain.Campaign
import br.com.bird.servicebirdad.infrastructure.adapter.controller.dto.CampaignDto
import br.com.bird.servicebirdad.infrastructure.adapter.controller.dto.CampaignResponseDto
import br.com.bird.servicebirdad.infrastructure.adapter.controller.dto.CampaignResultDto
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.time.ZoneId

@RestController
@RequestMapping("/campaigns")
class CampaignController(
    private val campaignUseCase: CampaignUseCase,
) {
    @GetMapping
    fun getByCompanyId(
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

    @PostMapping
    fun create(
        @RequestHeader("companyId") companyId: Long,
        @RequestBody campaignDto: CampaignDto
    ): ResponseEntity<Void> {
        println("Creating campaign for company $companyId - $campaignDto")
        campaignUseCase.createCampaign(
            companyId, Campaign(
                id = null,
                adName = campaignDto.budget.name,
                objective = campaignDto.objective,
                budgetType = campaignDto.budget.type,
                budgetValue = campaignDto.budget.amount,
                startDate = campaignDto.budget.scheduleStart.atZone(ZoneId.systemDefault()).toLocalDate(),
                endDate = campaignDto.budget.scheduleEnd.atZone(ZoneId.systemDefault()).toLocalDate(),
                companyId = companyId
            )
        )

        return ResponseEntity.created(URI.create("/campaigns")).build()
    }
}