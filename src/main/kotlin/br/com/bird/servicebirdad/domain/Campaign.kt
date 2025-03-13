package br.com.bird.servicebirdad.domain

import java.time.LocalDate

data class Campaign(
    val id: Long? = null,
    val adName: String,
    val objective: String,
    val budgetType: String,
    val budgetValue: Double,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val companyId: Long,
    val totems: List<Long>
)