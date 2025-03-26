package br.com.bird.servicebirdad.application.port.output

interface DashboardRepositoryPort {
    fun getData(): Number
    fun getDataByCompany(company: Long): Number
}