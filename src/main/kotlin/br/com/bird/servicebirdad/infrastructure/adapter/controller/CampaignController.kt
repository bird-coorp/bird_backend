package br.com.bird.servicebirdad.infrastructure.adapter.controller

import br.com.bird.servicebirdad.application.port.input.CampaignUseCase
import org.bouncycastle.util.test.FixedSecureRandom.BigInteger
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/campaigns")
class CampaignController(
    private val campaignUseCase: CampaignUseCase,
) {

    @PostMapping("/{companyId}")
    fun create(@PathVariable companyId: BigInteger) {
        campaignUseCase.createCampaign(companyId)
    }
}