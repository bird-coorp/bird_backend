package br.com.bird.servicebirdad.infrastructure.adapter.controller.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant

data class BudgetDto(
    val name: String,
    val type: String,
    val amount: Double,
    @JsonProperty(value = "schedule_start")
    val scheduleStart: Instant,
    @JsonProperty(value = "schedule_end")
    val scheduleEnd: Instant
)

data class LocalizationDto(
    val lat: Double,
    val lng: Double,
    val name: String
)

data class CampaignDto(
    val objective: String,
    val budget: BudgetDto,
    val localization: List<LocalizationDto>
)