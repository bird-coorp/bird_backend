package br.com.bird.servicebirdad.application.service

import br.com.bird.servicebirdad.application.port.input.FileUseCase
import br.com.bird.servicebirdad.application.port.output.CampaignFileRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class FileService(
    private val campaignFileRepository: CampaignFileRepository
): FileUseCase {
    @Cacheable("files")
    override fun getById(id: Long) = campaignFileRepository.findById(id)
}