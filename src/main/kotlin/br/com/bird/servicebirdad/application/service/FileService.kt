package br.com.bird.servicebirdad.application.service

import br.com.bird.servicebirdad.application.port.input.FileUseCase
import br.com.bird.servicebirdad.application.port.output.CampaignFilePort
import org.springframework.cache.annotation.Cacheable
import org.springframework.core.io.InputStreamResource
import org.springframework.stereotype.Service

@Service
class FileService(
    private val minioService: MinioService,
    private val campaignFileRepository: CampaignFilePort
) : FileUseCase {
    @Cacheable("fileUrl")
    override fun getFileUrl(fileId: Long): String {
        val file = campaignFileRepository.findById(fileId)
        return minioService.getFileUrl(file.filename)
    }
}