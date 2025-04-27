package br.com.bird.servicebirdad.application.port.input

import br.com.bird.servicebirdad.domain.Company
import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.CompanyEntity

interface CompanyUseCase {
    fun createCompany(company: Company): Company
    fun getByCompanyId(companyId: Long): CompanyEntity
}