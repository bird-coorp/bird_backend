package br.com.bird.servicebirdad.application.service

import io.minio.GetObjectArgs
import io.minio.GetPresignedObjectUrlArgs
import io.minio.MinioClient
import io.minio.PutObjectArgs
import io.minio.http.Method
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.InputStreamResource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.InputStream
import java.util.*
import java.util.concurrent.TimeUnit

@Service
class MinioService(
    @Autowired private val minioClient: MinioClient
) {

    @Value("\${minio.bucket-name}")
    private lateinit var bucketName: String

    fun upload(file: MultipartFile): String {
        return try {
            val fileName = "${UUID.randomUUID()} - ${file.originalFilename ?: file.name}"

            minioClient.putObject(
                PutObjectArgs.builder()
                    .bucket(bucketName)
                    .`object`(fileName)
                    .stream(file.inputStream, file.size, -1)
                    .contentType(file.contentType)
                    .build()
            )

            fileName
        } catch (e: Exception) {
            throw RuntimeException("Erro ao fazer upload do arquivo: ${e.message}", e)
        }
    }

    fun download(fileName: String): InputStreamResource {
        val stream: InputStream = minioClient.getObject(
            GetObjectArgs.builder()
                .bucket(bucketName)
                .`object`(fileName)
                .build()
        )

        return InputStreamResource(stream)
    }

    fun getFileUrl(fileName: String): String {
        val expiry = TimeUnit.HOURS.toSeconds(24)
        return minioClient.getPresignedObjectUrl(
            GetPresignedObjectUrlArgs.builder()
                .method(Method.GET)
                .bucket(bucketName)
                .`object`(fileName)
                .expiry(expiry.toInt())
                .build()
        )
    }
}