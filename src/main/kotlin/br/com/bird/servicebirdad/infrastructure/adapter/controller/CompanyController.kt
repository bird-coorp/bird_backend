package br.com.bird.servicebirdad.infrastructure.adapter.controller

import br.com.bird.servicebirdad.application.port.input.CompanyUseCase
import br.com.bird.servicebirdad.domain.Company
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/companies")
class CompanyController(private val companyUseCase: CompanyUseCase) {
    @PostMapping
    fun createCompany(@RequestBody company: Company): ResponseEntity<Company> {
        return ResponseEntity.ok(companyUseCase.createCompany(company))
    }
}