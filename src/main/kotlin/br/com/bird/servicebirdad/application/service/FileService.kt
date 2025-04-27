package br.com.bird.servicebirdad.application.service

import br.com.bird.servicebirdad.application.port.input.FileUseCase
import br.com.bird.servicebirdad.application.port.output.CampaignFilePort
import org.springframework.cache.annotation.Cacheable
import org.springframework.core.io.InputStreamResource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class FileService(
    private val minioService: MinioService,
    private val campaignFileRepository: CampaignFilePort
) : FileUseCase {
    @Cacheable("files")
    override fun getById(id: Long): Pair<InputStreamResource, String> {
        val file = campaignFileRepository.findById(id)
        return Pair(
            minioService.download(file.filename),
            file.filename
        )
    }
}