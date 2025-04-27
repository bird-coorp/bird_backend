package br.com.bird.servicebirdad.infrastructure.adapter.database.repository

import br.com.bird.servicebirdad.application.port.output.CheckoutPort
import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.PaymentIntentEntity
import br.com.bird.servicebirdad.infrastructure.adapter.database.repository.jpa.PaymentIntentJpaRepository
import org.springframework.stereotype.Component

@Component
class JpaCheckoutRepository(
    private val paymentIntentJpaRepository: PaymentIntentJpaRepository
) : CheckoutPort {
    override fun save(entity: PaymentIntentEntity): PaymentIntentEntity =
        paymentIntentJpaRepository.save(entity)

    override fun getByCompanyId(companyId: Long): List<PaymentIntentEntity> =
        paymentIntentJpaRepository.findByCompanyIdOrderByIdDesc(companyId)

    override fun getByPaymentIntentId(paymentIntentId: String) =
        paymentIntentJpaRepository.findByPaymentIntentIdRequest(paymentIntentIdRequest = paymentIntentId)
}