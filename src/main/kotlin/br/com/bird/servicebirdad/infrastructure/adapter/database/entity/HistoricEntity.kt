package br.com.bird.servicebirdad.infrastructure.adapter.database.entity

import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.Origin.WEB
import jakarta.persistence.*
import jakarta.persistence.EnumType.STRING
import java.time.LocalDateTime

@Entity
@Table(name = "historic")
data class HistoricEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Enumerated(STRING)
    @Convert(converter = StatusConverter::class)
    val since: Status,

    @Enumerated(STRING)
    @Convert(converter = StatusConverter::class)
    val until: Status,

    val createdAt: LocalDateTime,

    @Enumerated(STRING)
    val origin: Origin = WEB
)

enum class Origin {
    WEB,
    SCHEDULER
}