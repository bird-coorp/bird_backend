package br.com.bird.servicebirdad.application.port.output

import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.BankBalanceEntity
import java.util.Optional

interface BankBalancePort {
    fun getBankBalanceEntity(companyId: Long): Optional<BankBalanceEntity>
    fun save(bankBalanceEntity: BankBalanceEntity): BankBalanceEntity
}