package br.com.bird.servicebirdad.application.port.output

import br.com.bird.servicebirdad.domain.Company
import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.CompanyEntity

interface CompanyRepositoryPort {
    fun save(company: CompanyEntity): CompanyEntity
    fun findById(id: Long): CompanyEntity
    fun findAll(): List<Company>
    fun existsByCnpjOrEmail(cnpj: String, email: String): Boolean
}