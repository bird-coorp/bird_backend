package br.com.bird.servicebirdad.application.service

import br.com.bird.servicebirdad.application.port.input.CheckoutUseCase
import br.com.bird.servicebirdad.application.port.input.PaymentResponseDto
import br.com.bird.servicebirdad.application.port.output.BankBalancePort
import br.com.bird.servicebirdad.application.port.output.CheckoutPort
import br.com.bird.servicebirdad.application.service.dto.DataDto
import br.com.bird.servicebirdad.application.service.dto.PaymentIntentEventDto
import br.com.bird.servicebirdad.application.service.dto.WebhookFailedRootDto
import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.BankBalanceEntity
import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.PaymentIntentEntity
import com.google.gson.Gson
import com.stripe.model.Event
import com.stripe.model.PaymentIntent
import com.stripe.param.PaymentIntentCreateParams
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.math.BigDecimal.TEN
import java.math.BigDecimal.ZERO
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Service
class CheckoutService(
    private val tokenService: TokenService,
    private val companyService: CompanyService,
    private val checkoutPort: CheckoutPort,
    private val bankBalancePort: BankBalancePort,
) : CheckoutUseCase {

    @Transactional(rollbackOn = [Exception::class])
    override fun checkout(
        amount: BigDecimal,
        token: String,
        paymentIntentIdRequest: String
    ): Map<String, String> {
        val tokenData = tokenService.getTokenData(token)

        checkoutPort.save(
            PaymentIntentEntity(
                id = null,
                paymentIntentIdRequest = paymentIntentIdRequest,
                stripeId = null,
                amount = amount.divide(TEN * TEN),
                created = Instant.now(),
                companyId = tokenData.companyId,
                status = "PENDING",
            )
        )

        val params = PaymentIntentCreateParams.builder()
            .setAmount(amount.toLong())
            .setCurrency("brl")
            .putMetadata("companyId", tokenData.companyId.toString())
            .putMetadata("paymentIntentIdRequest", paymentIntentIdRequest)
            .build()

        val paymentIntent = PaymentIntent.create(params)
        return mapOf("clientSecret" to paymentIntent.clientSecret)
    }

    override fun getPaymentIntent(token: String): List<PaymentResponseDto> {
        val tokenData = tokenService.getTokenData(token)
        return checkoutPort.getByCompanyId(companyId = tokenData.companyId).map {
            PaymentResponseDto(
                id = it.id!!,
                amount = it.amount,
                createdAt = formatInstant(it.created),
                status = it.status?.uppercase() ?: "PENDING",
            )
        }
    }

    private fun getEventSuccessData(event: Event): Pair<String, DataDto> {
        val jsonData = Gson().toJson(event)
        val paymentIntentDtoJson = Gson().fromJson(jsonData, PaymentIntentEventDto::class.java)

        val paymentIntentId = paymentIntentDtoJson.data.paymentIntent.metadata.paymentIntentIdRequest

        val paymentIntentDto = paymentIntentDtoJson.data

        return Pair(
            paymentIntentId,
            paymentIntentDto
        )
    }

    private fun getEventFailedData(event: Event): Pair<String, WebhookFailedRootDto> {
        val jsonData = Gson().toJson(event.data.`object`)
        val paymentIntentDtoJson = Gson().fromJson(jsonData, WebhookFailedRootDto::class.java)
        val paymentIntentId = paymentIntentDtoJson.metadata.paymentIntentIdRequest

        return Pair(
            paymentIntentId,
            paymentIntentDtoJson
        )
    }

    private fun updateOrCreateBankBalance(companyId: Long, amountReceived: Long) {
        val company = companyService.getByCompanyId(companyId)
        val balance = bankBalancePort.getBankBalanceEntity(companyId)
            .orElseGet {
                BankBalanceEntity(
                    id = null,
                    balance = ZERO,
                    company = company
                )
            }

        val amount = amountReceived.toBigDecimal().movePointLeft(2)

        balance.balance = balance.balance.plus(amount)
        bankBalancePort.save(balance)
    }

    @Transactional(rollbackOn = [Exception::class])
    override fun paymentIntentSucceeded(
        data: Event
    ) {
        val (paymentIntentId, paymentIntent) = getEventSuccessData(event = data)

        val result = checkoutPort.getByPaymentIntentId(paymentIntentId)

        result.amountReceived = paymentIntent.paymentIntent.amountReceived
        result.livemode = paymentIntent.paymentIntent.livemode
        result.paymentMethodId = paymentIntent.paymentIntent.paymentMethod
        result.status = paymentIntent.paymentIntent.status
        result.stripeId = paymentIntent.paymentIntent.id
        result.metadata = mapOf(
            "paymentIntentIdRequest" to paymentIntent.paymentIntent.metadata.paymentIntentIdRequest,
            "companyId" to paymentIntent.paymentIntent.metadata.companyId
        )

        updateOrCreateBankBalance(
            companyId = result.companyId,
            amountReceived = paymentIntent.paymentIntent.amountReceived
        )

        checkoutPort.save(result)
    }

    override fun paymentIntentFailed(event: Event) {
        val (paymentIntentId, paymentIntent) = getEventFailedData(event)

        val result = checkoutPort.getByPaymentIntentId(paymentIntentId)

        result.amountReceived = paymentIntent.amountReceived
        result.livemode = paymentIntent.livemode
        result.paymentMethodId = paymentIntent.paymentMethodOptions.card.requestThreeDSecure
        result.status = paymentIntent.lastPaymentError?.code
        result.stripeId = paymentIntent.id
        result.metadata = mapOf(
            "paymentIntentIdRequest" to paymentIntent.metadata.paymentIntentIdRequest,
            "companyId" to paymentIntent.metadata.companyId
        )

        result.canceledAt = Instant.now()

        checkoutPort.save(result)
    }
}

fun formatInstant(instant: Instant): String {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy ss:mm:ss")
    val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
    return localDateTime.format(formatter)
}