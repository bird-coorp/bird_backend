package br.com.bird.servicebirdad.infrastructure.config

import com.stripe.Stripe
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class StripeInitializer(@Value("\${stripe.secret.key}") val secretKey: String) {

    @PostConstruct
    fun init() {
        Stripe.apiKey = secretKey
    }
}