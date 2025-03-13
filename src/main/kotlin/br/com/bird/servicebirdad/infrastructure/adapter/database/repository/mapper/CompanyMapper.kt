package br.com.bird.servicebirdad.infrastructure.adapter.database.repository.mapper

import br.com.bird.servicebirdad.domain.Company
import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.CompanyEntity

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