package br.com.bird.servicebirdad.infrastructure.adapter.database.entity

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "campaigns")
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator::class,
    property = "id"
)
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
    @Convert(converter = StatusConverter::class)
    var status: Status = Status.PENDING,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    val company: CompanyEntity,

    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "campaign_totem",
        joinColumns = [JoinColumn(name = "campaign_id")],
        inverseJoinColumns = [JoinColumn(name = "totem_id")]
    )
    val totems: MutableList<TotemEntity> = mutableListOf(),

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "campaign_historic",
        joinColumns = [JoinColumn(name = "campaign_id")],
        inverseJoinColumns = [JoinColumn(name = "historic_id")]
    )
    val historic: MutableList<HistoricEntity> = mutableListOf(),

    val fileId: Long? = null,
) {
    override fun toString(): String {
        return "CampaignEntity(id=$id, adName='$adName', objective='$objective', budgetType='$budgetType', budgetValue=$budgetValue, startDate=$startDate, endDate=$endDate, status=$status, createdAt=$createdAt, fileId=$fileId)"
    }
}

enum class Status {
    APPROVED, // QUANDO A CAMPANHA É APROVADA

    ACTIVE, // QUANDO A CAMPANHA ESTÁ EM VEICULAÇÃO -> REQUER ESTAR APROVADA -> SISTEMA DEVE ATIVAR AUTOMATICAMENTE

    INACTIVE, // QUANDO O USUÁRIO DESATIVA A CAMPANHA -> REQUER ESTAR ATIVA

    PENDING, // QUANDO A CAMPANHA ESTÁ AGUARDANDO APROVAÇÃO -> QUANDO CRIADA

    DENIED, // QUANDO A CAMPANHA É NEGADA POR ALGUM MOTIVO -> REQUER JUSTIFICATIVA -> REQUER ESTAR PENDENTE
}

@Converter(autoApply = true)
class StatusConverter : AttributeConverter<Status, String> {
    override fun convertToDatabaseColumn(attribute: Status?): String {
        return attribute?.name?.lowercase() ?: "pending"
    }

    override fun convertToEntityAttribute(dbData: String?): Status {
        return Status.values().find { it.name.lowercase() == dbData } ?: Status.PENDING
    }
}