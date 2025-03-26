package br.com.bird.servicebirdad.application.port.input

interface DashboardUseCase {
    fun getDashboardData(token: String): Number
}