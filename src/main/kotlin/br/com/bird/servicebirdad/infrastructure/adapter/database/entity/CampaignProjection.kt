package br.com.bird.servicebirdad.infrastructure.adapter.database.entity

interface CampaignProjection {
    fun getId(): Long
    fun getFileId(): Long?
}