package br.com.bird.servicebirdad.application.service

import br.com.bird.servicebirdad.application.port.input.CompanyUseCase
import br.com.bird.servicebirdad.application.port.output.CompanyRepositoryPort
import br.com.bird.servicebirdad.domain.Company
import br.com.bird.servicebirdad.domain.Profiles
import br.com.bird.servicebirdad.domain.exceptions.BusinessException
import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.CompanyEntity
import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.ProfileEntity
import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.UserEntity
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class CompanyService(
    @Autowired private val companyRepository: CompanyRepositoryPort,
    private val getProfiles: MutableList<ProfileEntity>,
) : CompanyUseCase {

    @Transactional(rollbackOn = [Exception::class])
    override fun createCompany(company: Company): Company {
        if (companyRepository.existsByCnpjOrEmail(company.cnpj, company.email)) {
            throw BusinessException("Já existe uma empresa cadastrada com este CNPJ ou E-mail.")
        }

        val savedCompany = companyRepository.save(
            CompanyEntity(
                id = null,
                companyName = company.companyName,
                fantasyName = company.fantasyName,
                cnpj = company.cnpj,
                email = company.email,
                phone = company.phone,
                users = mutableListOf()
            )
        )

        val adminProfile = getProfiles.find { it.id == Profiles.ADMIN }
            ?: throw BusinessException("Perfil não encontrado")

        val password = BCryptPasswordEncoder().encode(company.password)

        val user = UserEntity(
            id = null,
            name = company.companyName,
            email = company.email,
            credentials = password,
            company = savedCompany,
            profiles = mutableListOf(adminProfile)
        )

        savedCompany.users.add(user)

        companyRepository.save(savedCompany)

        return Company(
            id = savedCompany.id,
            companyName = savedCompany.companyName,
            fantasyName = savedCompany.fantasyName,
            cnpj = savedCompany.cnpj,
            email = savedCompany.email,
            phone = savedCompany.phone,
            password = "encrypted"
        )
    }
}