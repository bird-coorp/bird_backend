package br.com.bird.servicebirdad.infrastructure.adapter.database.repository.jpa

import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.PaymentIntentEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PaymentIntentJpaRepository : JpaRepository<PaymentIntentEntity, Long> {
    fun findByCompanyIdOrderByIdDesc(companyId: Long): List<PaymentIntentEntity>

    fun findByPaymentIntentIdRequest(paymentIntentIdRequest: String): PaymentIntentEntity
}