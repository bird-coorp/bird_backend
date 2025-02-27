package br.com.bird.servicebirdad.infrastructure.adapter.repository

import br.com.bird.servicebirdad.application.port.output.CompanyRepositoryPort
import br.com.bird.servicebirdad.domain.Company
import br.com.bird.servicebirdad.infrastructure.adapter.entity.CompanyEntity
import br.com.bird.servicebirdad.infrastructure.adapter.repository.jpa.CompanyJpaRepository
import br.com.bird.servicebirdad.infrastructure.adapter.repository.mapper.CompanyMapper
import org.springframework.stereotype.Repository

@Repository
class JpaCompanyRepository(private val jpaRepository: CompanyJpaRepository) : CompanyRepositoryPort {
    override fun save(company: CompanyEntity): CompanyEntity {
        return jpaRepository.save(company)
    }

    override fun findById(id: Long): Company? {
        return jpaRepository.findById(id).map(CompanyMapper::toDomain).orElse(null)
    }

    override fun findAll(): List<Company> {
        return jpaRepository.findAll().map(CompanyMapper::toDomain)
    }

    override fun existsByCnpjOrEmail(cnpj: String, email: String): Boolean {
        return jpaRepository.existsByCnpjOrEmail(cnpj, email)
    }
}