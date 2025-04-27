package br.com.bird.servicebirdad.infrastructure.adapter.controller

import br.com.bird.servicebirdad.application.port.input.BalanceUseCase
import br.com.bird.servicebirdad.application.service.dto.BankBalanceDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/bank-balance")
class BankBalanceController(
    private val balanceUseCase: BalanceUseCase
) {
    @GetMapping
    fun get(
        @RequestHeader("Authorization") token: String,
    ): ResponseEntity<BankBalanceDto> {
        val response = balanceUseCase.getBalance(token)
        return ResponseEntity.ok(response)
    }
}