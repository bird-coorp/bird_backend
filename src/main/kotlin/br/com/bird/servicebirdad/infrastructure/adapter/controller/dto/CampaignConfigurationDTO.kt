package br.com.bird.servicebirdad.infrastructure.adapter.controller.dto

import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.Status

data class CampaignConfigurationDTO(
    val status: Status,
    val reason: String? = null,
)