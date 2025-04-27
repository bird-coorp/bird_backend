package br.com.bird.servicebirdad.application.port.input

import br.com.bird.servicebirdad.application.service.dto.BankBalanceDto

interface BalanceUseCase {
    fun getBalance(token: String): BankBalanceDto
}