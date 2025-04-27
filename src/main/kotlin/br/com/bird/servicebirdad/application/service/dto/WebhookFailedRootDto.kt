package br.com.bird.servicebirdad.application.service.dto

import com.google.gson.annotations.SerializedName

data class WebhookFailedRootDto(
    @SerializedName("amount")
    val amount: Int,

    @SerializedName("amount_capturable")
    val amountCapturable: Int,

    @SerializedName("amount_details")
    val amountDetails: AmountDetailsDto,

    @SerializedName("amount_received")
    val amountReceived: Long,

    @SerializedName("automatic_payment_methods")
    val automaticPaymentMethods: AutomaticPaymentMethodsDto,

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

    @SerializedName("id")
    val id: String,

    @SerializedName("last_payment_error")
    val lastPaymentError: LastPaymentErrorDto?,

    @SerializedName("latest_charge")
    val latestCharge: LatestChargeDto?,

    @SerializedName("livemode")
    val livemode: Boolean,

    @SerializedName("metadata")
    val metadata: MetadataDto,

    @SerializedName("object")
    val objectType: String,

    @SerializedName("payment_method_configuration_details")
    val paymentMethodConfigurationDetails: PaymentMethodConfigurationDetailsDto,

    @SerializedName("payment_method_options")
    val paymentMethodOptions: PaymentMethodOptionsDto,

    @SerializedName("payment_method_types")
    val paymentMethodTypes: List<String>,

    @SerializedName("status")
    val status: String
)

data class AmountDetailsDto(
    @SerializedName("tip")
    val tip: Map<String, Any> = emptyMap()
)

data class AutomaticPaymentMethodsDto(
    @SerializedName("allow_redirects")
    val allowRedirects: String,

    @SerializedName("enabled")
    val enabled: Boolean
)

data class LastPaymentErrorDto(
    @SerializedName("charge")
    val charge: String,

    @SerializedName("code")
    val code: String,

    @SerializedName("decline_code")
    val declineCode: String,

    @SerializedName("doc_url")
    val docUrl: String,

    @SerializedName("message")
    val message: String,

    @SerializedName("payment_method")
    val paymentMethod: PaymentMethodDto,

    @SerializedName("type")
    val type: String
)

data class PaymentMethodDto(
    @SerializedName("billing_details")
    val billingDetails: BillingDetailsDto,

    @SerializedName("card")
    val card: CardDto,

    @SerializedName("created")
    val created: Long,

    @SerializedName("id")
    val id: String,

    @SerializedName("livemode")
    val livemode: Boolean,

    @SerializedName("metadata")
    val metadata: Map<String, Any> = emptyMap(),

    @SerializedName("object")
    val objectType: String,

    @SerializedName("type")
    val type: String
)

data class BillingDetailsDto(
    @SerializedName("address")
    val address: AddressDto
)

data class AddressDto(
    @SerializedName("postal_code")
    val postalCode: String
)

data class CardDto(
    @SerializedName("brand")
    val brand: String,

    @SerializedName("checks")
    val checks: ChecksDto,

    @SerializedName("country")
    val country: String,

    @SerializedName("exp_month")
    val expMonth: Int,

    @SerializedName("exp_year")
    val expYear: Int,

    @SerializedName("fingerprint")
    val fingerprint: String,

    @SerializedName("funding")
    val funding: String,

    @SerializedName("last4")
    val last4: String,

    @SerializedName("networks")
    val networks: NetworksDto,

    @SerializedName("three_d_secure_usage")
    val threeDSecureUsage: ThreeDSecureUsageDto
)

data class ChecksDto(
    @SerializedName("address_postal_code_check")
    val addressPostalCodeCheck: String,

    @SerializedName("cvc_check")
    val cvcCheck: String
)

data class NetworksDto(
    @SerializedName("available")
    val available: List<String>
)

data class ThreeDSecureUsageDto(
    @SerializedName("supported")
    val supported: Boolean
)

data class LatestChargeDto(
    @SerializedName("id")
    val id: String
)

data class MetadataDto(
    @SerializedName("paymentIntentIdRequest")
    val paymentIntentIdRequest: String,

    @SerializedName("companyId")
    val companyId: String
)

data class PaymentMethodConfigurationDetailsDto(
    @SerializedName("id")
    val id: String
)

data class PaymentMethodOptionsDto(
    @SerializedName("card")
    val card: CardOptionsDto
)

data class CardOptionsDto(
    @SerializedName("request_three_d_secure")
    val requestThreeDSecure: String
)