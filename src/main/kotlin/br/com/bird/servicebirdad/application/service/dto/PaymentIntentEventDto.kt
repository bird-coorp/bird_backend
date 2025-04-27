package br.com.bird.servicebirdad.application.service.dto

import com.google.gson.annotations.SerializedName

data class PaymentIntentEventDto(
    @SerializedName("api_version")
    val apiVersion: String,

    @SerializedName("created")
    val created: Long,

    @SerializedName("data")
    val data: DataDto,

    @SerializedName("id")
    val id: String,

    @SerializedName("livemode")
    val livemode: Boolean,

    @SerializedName("object")
    val `object`: String,

    @SerializedName("pending_webhooks")
    val pendingWebhooks: Int,

    @SerializedName("request")
    val request: RequestDto?,

    @SerializedName("type")
    val type: String
)

data class DataDto(
    @SerializedName("object")
    val paymentIntent: PaymentIntentDto
)

data class PaymentIntentDto(
    @SerializedName("id")
    val id: String,

    @SerializedName("object")
    val `object`: String,

    @SerializedName("amount")
    val amount: Long,

    @SerializedName("amount_capturable")
    val amountCapturable: Long,

    @SerializedName("amount_details")
    val amountDetails: AmountDetailsDto,

    @SerializedName("amount_received")
    val amountReceived: Long,

    @SerializedName("automatic_payment_methods")
    val automaticPaymentMethods: AutomaticPaymentMethods,

    @SerializedName("capture_method")
    val captureMethod: String,

    @SerializedName("client_secret")
    val clientSecret: String,

    @SerializedName("confirmation_method")
    val confirmationMethod: String,

    @SerializedName("created")
    val created: Long,

    @SerializedName("currency")
    val currency: String,

    @SerializedName("latest_charge")
    val latestCharge: String,

    @SerializedName("livemode")
    val livemode: Boolean,

    @SerializedName("metadata")
    val metadata: Metadata,

    @SerializedName("payment_method")
    val paymentMethod: String,

    @SerializedName("payment_method_configuration_details")
    val paymentMethodConfigurationDetails: PaymentMethodConfigurationDetails,

    @SerializedName("payment_method_options")
    val paymentMethodOptions: PaymentMethodOptions,

    @SerializedName("payment_method_types")
    val paymentMethodTypes: List<String>,

    @SerializedName("status")
    val status: String
)

data class AutomaticPaymentMethods(
    @SerializedName("allow_redirects")
    val allowRedirects: String,

    @SerializedName("enabled")
    val enabled: Boolean
)

data class Metadata(
    @SerializedName("paymentIntentIdRequest")
    val paymentIntentIdRequest: String,

    @SerializedName("companyId")
    val companyId: String
)

data class PaymentMethodConfigurationDetails(
    @SerializedName("id")
    val id: String
)

data class PaymentMethodOptions(
    @SerializedName("card")
    val card: Card
)

data class Card(
    @SerializedName("request_three_d_secure")
    val requestThreeDSecure: String
)

data class RequestDto(
    @SerializedName("id")
    val id: String,

    @SerializedName("idempotency_key")
    val idempotencyKey: String
)