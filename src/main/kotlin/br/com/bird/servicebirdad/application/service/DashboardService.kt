package br.com.bird.servicebirdad.application.service

import br.com.bird.servicebirdad.application.port.input.DashboardUseCase
import br.com.bird.servicebirdad.application.port.output.DashboardRepositoryPort
import org.springframework.stereotype.Component

@Component
class DashboardService(
    private val tokenService: TokenService,
    private val dashboardRepositoryPort: DashboardRepositoryPort
) : DashboardUseCase {
    override fun getDashboardData(token: String): Number {
        val tokenData = tokenService.getTokenData(token)

        val isAdmin = tokenData.role.contains("ADMIN")

        return if (isAdmin) {
            dashboardRepositoryPort.getData()
        } else {
            dashboardRepositoryPort.getDataByCompany(tokenData.companyId)
        }

    }
}