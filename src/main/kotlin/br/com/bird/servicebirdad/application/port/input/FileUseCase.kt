package br.com.bird.servicebirdad.application.port.input

import org.springframework.core.io.InputStreamResource

interface FileUseCase {
    fun getById(id: Long): Pair<InputStreamResource, String>
}