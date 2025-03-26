package br.com.bird.servicebirdad.application.service

import br.com.bird.servicebirdad.application.port.input.ScheduleUseCase
import br.com.bird.servicebirdad.application.port.output.CampaignRepositoryPort
import br.com.bird.servicebirdad.application.port.output.ScheduleRepositoryPort
import br.com.bird.servicebirdad.domain.exceptions.BusinessException
import br.com.bird.servicebirdad.infrastructure.adapter.controller.dto.ScheduleDTO
import br.com.bird.servicebirdad.infrastructure.adapter.controller.dto.ScheduleResponse
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ISO_LOCAL_TIME

@Component
class ScheduleService(
    private val scheduleRepository: ScheduleRepositoryPort,
    private val campaignRepository: CampaignRepositoryPort,
) : ScheduleUseCase {
    override fun getSchedule(totemId: Long): ScheduleResponse {

        val schedule = scheduleRepository.findScheduleByTotemId(totemId)
            ?: throw BusinessException("Schedule not found for Totem ID $totemId")

        val startTime = schedule.from
        val endTime = schedule.to

        val timeSlots = generateTimeSlots(start = startTime, end = endTime)

        val campaigns = campaignRepository.findActiveCampaignsByTotemId(totemId)
        if (campaigns.isEmpty()) throw BusinessException("No active campaigns for Totem ID $totemId")

        val scheduleList = mutableListOf<ScheduleDTO>()
        val campaignCount = campaigns.size

        timeSlots.forEachIndexed { index, time ->
            val campaign = campaigns[index % campaignCount]
            scheduleList.add(
                ScheduleDTO(
                    time = time.format(ISO_LOCAL_TIME),
                    fileId = campaign.getFileId()!!
                )
            )
        }

        return ScheduleResponse(
            schedule = scheduleList,
            mediasIds = scheduleList.map { it.fileId }.toMutableSet()
        )
    }

    private fun generateTimeSlots(start: LocalTime, end: LocalTime): List<LocalTime> {
        println("Generating slots from $start to $end")

        val today = LocalDate.now()
        val tomorrow = today.plusDays(1)

        val startDateTime = LocalDateTime.of(today, start)
        val endDateTime = if (end.isAfter(start)) LocalDateTime.of(today, end) else LocalDateTime.of(tomorrow, end)

        val slots = mutableListOf<LocalTime>()
        var current = startDateTime

        while (!current.isAfter(endDateTime)) {
            println("Add slot ${current.toLocalTime()}")
            slots.add(current.toLocalTime())
            current = current.plusSeconds(30)
        }

        return slots
    }

}