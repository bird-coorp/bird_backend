package br.com.bird.servicebirdad.infrastructure.adapter.controller

import br.com.bird.servicebirdad.application.port.input.FileUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpHeaders.CONTENT_DISPOSITION
import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

@RestController
@RequestMapping("/media")
class MediaController(
    private val fileUseCase: FileUseCase
) {
    @GetMapping("/{fileId}")
    fun getMedia(@PathVariable fileId: Long): ResponseEntity<ByteArray> {
        val response = fileUseCase.getById(fileId)

        val headers = HttpHeaders().apply {
            add(CONTENT_TYPE, response.mimeType)
            add(CONTENT_DISPOSITION, "inline; filename=\"${response.filename}\"")
        }

        return ResponseEntity(response.content, headers, HttpStatus.OK)
    }
}