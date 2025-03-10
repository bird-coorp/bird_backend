package br.com.bird.servicebirdad.infrastructure.adapter.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "campaigns")
data class CampaignEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "ad_name", nullable = false)
    val adName: String,

    @Column(name = "objective", nullable = false)
    val objective: String,

    @Column(name = "budget_type", nullable = false)
    val budgetType: String,

    @Column(name = "budget_value", nullable = false)
    val budgetValue: Double,

    @Column(name = "start_date", nullable = false)
    val startDate: LocalDate,

    @Column(name = "end_date", nullable = false)
    val endDate: LocalDate,

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    val status: Status = Status.PENDING,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    @JsonBackReference
    val company: CompanyEntity,

    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "campaign_totem",
        joinColumns = [JoinColumn(name = "campaign_id")],
        inverseJoinColumns = [JoinColumn(name = "totem_id")]
    )
    val totens: MutableList<TotemEntity> = mutableListOf()
)

enum class Status {
    ACTIVE,
    INACTIVE,
    PENDING,
    BLOCKING,
    DRAFT
}