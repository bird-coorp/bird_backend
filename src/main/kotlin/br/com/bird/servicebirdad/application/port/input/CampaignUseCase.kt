package br.com.bird.servicebirdad.application.port.input

import org.bouncycastle.util.test.FixedSecureRandom.BigInteger

interface CampaignUseCase {
    fun createCampaign(companyId: BigInteger)
}