package br.com.bird.servicebirdad.infrastructure.adapter.database.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "files")
data class CampaignFileEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val content: ByteArray,

    val mimeType: String,

    val filename: String,

    val createdAt: LocalDateTime = LocalDateTime.now(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CampaignFileEntity

        if (id != other.id) return false
        if (!content.contentEquals(other.content)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + content.contentHashCode()
        return result
    }
}