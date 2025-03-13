package br.com.bird.servicebirdad.infrastructure.adapter.controller.dto

import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.CampaignEntity
import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.CompanyEntity
import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.TotemEntity
import java.time.format.DateTimeFormatter.ISO_DATE
import java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME

data class CampaignDetailDTO(
    val id: Long?,
    val adName: String,
    val objective: String,
    val budgetType: String,
    val budgetValue: Double,
    val startDate: String,
    val endDate: String,
    val status: String,
    val createdAt: String,
    val fileId: Long?,
    val company: CompanyDTO,
    val totems: List<TotemDTO>
) {
    companion object {
        fun fromEntity(entity: CampaignEntity): CampaignDetailDTO {
            return CampaignDetailDTO(
                id = entity.id,
                adName = entity.adName,
                objective = entity.objective,
                budgetType = entity.budgetType,
                budgetValue = entity.budgetValue,
                startDate = entity.startDate.format(ISO_DATE),
                endDate = entity.endDate.format(ISO_DATE),
                status = entity.status.name,
                createdAt = entity.createdAt.format(ISO_LOCAL_DATE_TIME),
                fileId = entity.fileId,
                company = CompanyDTO.fromEntity(entity.company),
                totems = entity.totems.map { TotemDTO.fromEntity(it) }
            )
        }
    }
}

data class CompanyDTO(
    val id: Long?,
    val companyName: String,
    val fantasyName: String,
    val cnpj: String,
    val email: String,
    val phone: String
) {
    companion object {
        fun fromEntity(entity: CompanyEntity): CompanyDTO {
            return CompanyDTO(
                id = entity.id,
                companyName = entity.companyName,
                fantasyName = entity.fantasyName,
                cnpj = entity.cnpj,
                email = entity.email,
                phone = entity.phone
            )
        }
    }
}

data class TotemDTO(
    val id: Long?,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val enabled: Boolean
) {
    companion object {
        fun fromEntity(entity: TotemEntity): TotemDTO {
            return TotemDTO(
                id = entity.id,
                name = entity.name,
                latitude = entity.latitude,
                longitude = entity.longitude,
                enabled = entity.enabled
            )
        }
    }
}
