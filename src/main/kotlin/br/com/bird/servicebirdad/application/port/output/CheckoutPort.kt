package br.com.bird.servicebirdad.application.port.output

import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.PaymentIntentEntity

interface CheckoutPort {
    fun save(entity: PaymentIntentEntity): PaymentIntentEntity
    fun getByCompanyId(companyId: Long): List<PaymentIntentEntity>
    fun getByPaymentIntentId(paymentIntentId: String): PaymentIntentEntity
}