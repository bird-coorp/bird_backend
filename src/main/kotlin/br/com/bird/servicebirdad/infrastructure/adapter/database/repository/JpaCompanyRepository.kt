package br.com.bird.servicebirdad.infrastructure.adapter.database.repository

import br.com.bird.servicebirdad.application.port.output.CompanyRepositoryPort
import br.com.bird.servicebirdad.domain.Company
import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.CompanyEntity
import br.com.bird.servicebirdad.infrastructure.adapter.database.repository.jpa.CompanyJpaRepository
import br.com.bird.servicebirdad.infrastructure.adapter.database.repository.mapper.CompanyMapper
import jakarta.transaction.Transactional
import org.springframework.stereotype.Repository

@Repository
class JpaCompanyRepository(private val jpaRepository: CompanyJpaRepository) : CompanyRepositoryPort {

    @Transactional(rollbackOn = [Exception::class])
    override fun save(company: CompanyEntity) = jpaRepository.save(company)

    override fun findById(id: Long): CompanyEntity = jpaRepository.findById(id).orElseThrow()

    override fun findAll(): List<Company> = jpaRepository.findAll().map(CompanyMapper::toDomain)

    override fun existsByCnpjOrEmail(cnpj: String, email: String) = jpaRepository.existsByCnpjOrEmail(cnpj, email)
}