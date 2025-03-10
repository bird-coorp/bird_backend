package br.com.bird.servicebirdad.domain

data class Company(
    val id: Long? = null,
    val companyName: String,
    val fantasyName: String,
    val cnpj: String,
    val email: String,
    val phone: String,
    val password: String,
)