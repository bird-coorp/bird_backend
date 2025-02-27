package br.com.bird.servicebirdad.application.port.input

import br.com.bird.servicebirdad.domain.Company

interface CompanyUseCase {
    fun createCompany(company: Company): Company
}