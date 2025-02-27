package br.com.bird.servicebirdad.infrastructure.adapter.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "companies")
data class CompanyEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "company_name", nullable = false)
    val companyName: String,

    @Column(name = "fantasy_name", nullable = false)
    val fantasyName: String,

    @Column(name = "cnpj", nullable = false, unique = true, length = 18)
    val cnpj: String,

    @Column(name = "email", nullable = false, unique = true)
    val email: String,

    @Column(name = "phone", nullable = false, length = 15)
    val phone: String,

    @OneToMany(mappedBy = "company", cascade = [CascadeType.ALL], fetch = FetchType.LAZY, orphanRemoval = true)
    val users: MutableList<UserEntity> = mutableListOf(),

    @OneToMany(mappedBy = "firm", cascade = [CascadeType.ALL], fetch = FetchType.LAZY, orphanRemoval = true)
    val campaigns: MutableList<CampaignEntity> = mutableListOf(),
)