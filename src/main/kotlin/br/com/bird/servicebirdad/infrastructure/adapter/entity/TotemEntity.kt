package br.com.bird.servicebirdad.infrastructure.adapter.entity

import jakarta.persistence.*

@Entity
@Table(name = "totens")
class TotemEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val name: String,

    val latitude: Double,

    val longitude: Double,

    val enabled: Boolean = true,

    @ManyToMany(mappedBy = "totens", fetch = FetchType.LAZY)
    val campaigns: MutableList<CampaignEntity> = mutableListOf()
)