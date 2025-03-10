package br.com.bird.servicebirdad.domain

data class Totem(
    val id: Long? = null,
    val name: String,
    val longitude: Double,
    val latitude: Double,
    val enabled: Boolean = true
)