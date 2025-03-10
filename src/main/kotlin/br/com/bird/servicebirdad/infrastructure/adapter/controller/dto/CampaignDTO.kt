package br.com.bird.servicebirdad.infrastructure.adapter.controller.dto

import br.com.bird.servicebirdad.infrastructure.adapter.entity.CampaignEntity
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant
import java.time.format.DateTimeFormatter

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

data class CampaignResultDto(
    val id: Long,
    val companyId: Long,
    val status: String,
    val goal: String,
    val name: String,
    val from: String,
    val to: String,
    @JsonProperty(value = "created_at")
    val createdAt: String,
) {
    companion object {
        fun fromEntity(it: CampaignEntity, companyId: Long): CampaignResultDto {
            return CampaignResultDto(
                id = it.id!!,
                companyId = companyId,
                status = it.status.name,
                goal = it.objective,
                name = it.adName,
                from = it.startDate.format(DateTimeFormatter.ISO_DATE),
                to = it.endDate.format(DateTimeFormatter.ISO_DATE),
                createdAt = it.createdAt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            )
        }
    }
}

data class CampaignResponseDto(
    val content: List<CampaignResultDto>,
    val page: Int,
    val size: Int,
)