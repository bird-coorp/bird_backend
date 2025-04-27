package br.com.bird.servicebirdad.infrastructure.adapter.database.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "files")
data class CampaignFileEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val mimeType: String,

    val filename: String,

    val createdAt: LocalDateTime = LocalDateTime.now(),
)