package br.com.bird.servicebirdad.application.service

import br.com.bird.servicebirdad.application.port.input.CampaignUseCase
import org.bouncycastle.util.test.FixedSecureRandom
import org.springframework.stereotype.Component

@Component
class CampaignService : CampaignUseCase {
    override fun createCampaign(companyId: FixedSecureRandom.BigInteger) {
        TODO("Not yet implemented")
    }
}