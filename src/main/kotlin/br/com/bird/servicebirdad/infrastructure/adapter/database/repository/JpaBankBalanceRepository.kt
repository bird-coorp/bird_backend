package br.com.bird.servicebirdad.infrastructure.adapter.database.repository

import br.com.bird.servicebirdad.application.port.output.BankBalancePort
import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.BankBalanceEntity
import br.com.bird.servicebirdad.infrastructure.adapter.database.repository.jpa.BankBalanceJpaRepository
import org.springframework.stereotype.Component

@Component
class JpaBankBalanceRepository(
    private val bankBalanceJpaRepository: BankBalanceJpaRepository
) : BankBalancePort {
    override fun getBankBalanceEntity(companyId: Long) =
        bankBalanceJpaRepository.findByCompanyId(companyId)

    override fun save(bankBalanceEntity: BankBalanceEntity) =
        bankBalanceJpaRepository.save(bankBalanceEntity)
}