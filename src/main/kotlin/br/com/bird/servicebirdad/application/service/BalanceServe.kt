package br.com.bird.servicebirdad.application.service

import br.com.bird.servicebirdad.application.port.input.BalanceUseCase
import br.com.bird.servicebirdad.application.port.output.BankBalancePort
import br.com.bird.servicebirdad.application.port.output.UserRepositoryPort
import br.com.bird.servicebirdad.application.service.dto.BankBalanceDto
import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.BankBalanceEntity
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.math.BigDecimal.ZERO

@Service
class BalanceServe(
    @Autowired private val tokenService: TokenService,
    @Autowired private val userRepositoryPort: UserRepositoryPort,
    @Autowired private val bankBalancePort: BankBalancePort,
) : BalanceUseCase {

    @Transactional(rollbackOn = [Exception::class])
    override fun getBalance(token: String): BankBalanceDto {
        val token = tokenService.getTokenData(token = token)
        val user = userRepositoryPort.findById(id = token.id).orElseThrow()

        val balanceEntity = bankBalancePort.getBankBalanceEntity(
            companyId = user.company.id!!
        )
            .orElseGet {
                BankBalanceEntity(
                    id = null,
                    balance = ZERO,
                    company = user.company
                )
            }

        return BankBalanceDto(
            balance = balanceEntity.balance
        )
    }
}