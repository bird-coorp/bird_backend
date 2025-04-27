package br.com.bird.servicebirdad.application.port.input

import com.stripe.model.Event
import java.math.BigDecimal

interface CheckoutUseCase {
    fun checkout(
        amount: BigDecimal,
        token: String,
        paymentIntentIdRequest: String,
    ): Map<String, String>

    fun getPaymentIntent(
        token: String
    ): List<PaymentResponseDto>

    fun paymentIntentSucceeded(event: Event)
    fun paymentIntentFailed(event: Event)
}

data class PaymentResponseDto(
    val id: Long,
    val amount: BigDecimal,
    val createdAt: String,
    val status: String,
)