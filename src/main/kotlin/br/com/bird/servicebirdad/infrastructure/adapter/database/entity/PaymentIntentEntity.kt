package br.com.bird.servicebirdad.infrastructure.adapter.database.entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.Instant

@Entity
@Table(
    name = "payment_intents",
    indexes = [Index(name = "idx_stripe_id", columnList = "stripe_id", unique = true)]
)
data class PaymentIntentEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "payment_intent_request", nullable = false, unique = true)
    val paymentIntentIdRequest: String,

    @Column(name = "stripe_id", unique = true, length = 100)
    var stripeId: String? = null,

    @Column(name = "amount", nullable = false)
    val amount: BigDecimal,

    @Column(name = "amount_received")
    var amountReceived: Long? = null,

    @Column(name = "currency", nullable = false, length = 10)
    val currency: String = "BRL",

    @Column(name = "created", nullable = false)
    val created: Instant,

    @Column(name = "status", nullable = false, length = 50)
    var status: String? = null,

    @Column(name = "company_id", length = 100)
    val companyId: Long,

    @Column(name = "payment_method_id", length = 100)
    var paymentMethodId: String? = null,

    @Column(name = "livemode", nullable = false)
    var livemode: Boolean = false,

    @Column(name = "canceled_at")
    var canceledAt: Instant? = null,

    @ElementCollection
    @CollectionTable(
        name = "payment_intent_metadata",
        joinColumns = [JoinColumn(name = "payment_intent_id", referencedColumnName = "id")]
    )
    @MapKeyColumn(name = "meta_key")
    @Column(name = "meta_value")
    var metadata: Map<String, String>? = null
)