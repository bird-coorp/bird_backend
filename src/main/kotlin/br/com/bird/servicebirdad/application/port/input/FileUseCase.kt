package br.com.bird.servicebirdad.application.port.input

interface FileUseCase {
    fun getFileUrl(fileId: Long): Pair<String, Long>
}