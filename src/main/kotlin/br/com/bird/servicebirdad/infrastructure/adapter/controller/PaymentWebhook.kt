package br.com.bird.servicebirdad.infrastructure.adapter.controller

import br.com.bird.servicebirdad.application.port.input.CheckoutUseCase
import com.stripe.net.Webhook
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/webhook")
class PaymentWebhook(
    private val checkoutUseCase: CheckoutUseCase
) {

    private val logger = LoggerFactory.getLogger(PaymentWebhook::class.java)

    private val secret =
        "whsec_80d558322b658111f98c1222a80fccc1eb9aadfef9a48a2fe888d6ed1163f355"

    @PostMapping
    fun handleWebhook(
        @RequestBody payload: String,
        @RequestHeader("Stripe-Signature") sigHeader: String
    ): ResponseEntity<String?> {
        val endpointSecret = secret
        val event = Webhook.constructEvent(payload, sigHeader, endpointSecret)

        when (event.type) {
            "payment_intent.succeeded" -> {
                checkoutUseCase.paymentIntentSucceeded(
                    event = event
                )

            }

            "payment_intent.payment_failed" -> {
                checkoutUseCase.paymentIntentFailed(
                    event = event
                )
            }
        }

        return ResponseEntity.ok("")
    }
}