package br.com.bird.servicebirdad.infrastructure.adapter.controller

import br.com.bird.servicebirdad.application.port.input.CampaignUseCase
import br.com.bird.servicebirdad.domain.Campaign
import br.com.bird.servicebirdad.infrastructure.adapter.controller.dto.BudgetDto
import br.com.bird.servicebirdad.infrastructure.adapter.controller.dto.CampaignDto
import org.bouncycastle.util.test.FixedSecureRandom.BigInteger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.time.ZoneId

@RestController
@RequestMapping("/campaigns")
class CampaignController(
    private val campaignUseCase: CampaignUseCase,
) {

    @PostMapping("/{companyId}")
    fun create(@PathVariable companyId: Long, @RequestBody campaignDto: CampaignDto): ResponseEntity<Void> {
        println("Creating campaign for company $companyId - $campaignDto")
        val result = campaignUseCase.createCampaign(
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

        return ResponseEntity.created(URI.create("/campaigns/${result.id}")).build()
    }
}