package br.com.bird.servicebirdad.infrastructure.adapter.repository.mapper

import br.com.bird.servicebirdad.domain.Company
import br.com.bird.servicebirdad.infrastructure.adapter.entity.CompanyEntity

object CompanyMapper {
    fun toDomain(entity: CompanyEntity) = Company(
        id = entity.id,
        companyName = entity.companyName,
        fantasyName = entity.fantasyName,
        cnpj = entity.cnpj,
        email = "",
        phone = "",
        password = ""
    )
}