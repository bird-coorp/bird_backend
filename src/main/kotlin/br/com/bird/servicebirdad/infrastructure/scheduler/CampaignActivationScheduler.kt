package br.com.bird.servicebirdad.infrastructure.scheduler

import br.com.bird.servicebirdad.application.port.output.CampaignRepositoryPort
import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.HistoricEntity
import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.Origin
import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.Status
import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.Status.APPROVED
import br.com.bird.servicebirdad.infrastructure.adapter.database.repository.JpaHistoricRepository
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime.now
import java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME

@Component
class CampaignActivationScheduler(
    private val campaignRepositoryPort: CampaignRepositoryPort,
    private val jpaHistoricRepository: JpaHistoricRepository,
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Transactional(rollbackOn = [Exception::class])
    @Scheduled(cron = "\${campaign.activation.cron}")
    fun activateApprovedCampaigns() {
        logger.info(
            "Starting activation of approved campaigns ${
                now().format(ISO_LOCAL_DATE_TIME)
            }"
        )

        val campaigns = campaignRepositoryPort.findByStatus(APPROVED)

        campaigns.map {
            val historic = jpaHistoricRepository.save(HistoricEntity(
                id = null,
                since = it.status,
                until = Status.ACTIVE,
                createdAt = now(),
                origin = Origin.SCHEDULER
            ))

            it.historic.add(historic)
            it.status = Status.ACTIVE

            campaignRepositoryPort.save(it)
        }
    }
}