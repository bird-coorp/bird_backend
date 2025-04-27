package br.com.bird.servicebirdad.infrastructure.adapter.controller

import br.com.bird.servicebirdad.application.port.input.CheckoutUseCase
import br.com.bird.servicebirdad.application.port.input.PaymentResponseDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@RestController
@RequestMapping("/payments")
class PaymentController(
    private val checkoutUseCase: CheckoutUseCase
) {
    @PostMapping("/create-payment-intent")
    fun post(
        @RequestBody request: PaymentRequestDto,
        @RequestHeader("Authorization") token: String,
    ): ResponseEntity<Map<String, String>> {
        val response = checkoutUseCase.checkout(
            amount = request.amount,
            paymentIntentIdRequest = request.paymentIntentIdRequest,
            token = token
        )
        return ResponseEntity.ok(response)
    }

    @GetMapping
    fun get(
        @RequestHeader("Authorization") token: String,
    ): ResponseEntity<List<PaymentResponseDto>> =
        ResponseEntity.ok(checkoutUseCase.getPaymentIntent(token))
}

data class PaymentRequestDto(
    val amount: BigDecimal,
    val paymentIntentIdRequest: String
)