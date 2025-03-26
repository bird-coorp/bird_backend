package br.com.bird.servicebirdad.infrastructure.adapter.controller

import br.com.bird.servicebirdad.application.manager.SseEmitterManager
import br.com.bird.servicebirdad.application.manager.SseEvent
import br.com.bird.servicebirdad.domain.Profiles
import br.com.bird.servicebirdad.domain.Profiles.ADMIN
import br.com.bird.servicebirdad.domain.RequiresProfile
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

@RestController
@RequestMapping("/sse")
class SseController(
    private val sseEmitterManager: SseEmitterManager
) {

    @GetMapping
    fun get() {
        sseEmitterManager.log()
    }

    @GetMapping("/connect/{clientId}")
    fun connect(@PathVariable clientId: String): SseEmitter {
        return sseEmitterManager.addClient(clientId)
    }

    @RequiresProfile(profile = "ADMIN")
    @PostMapping("/send/{clientId}")
    fun notify(
        @PathVariable clientId: String,
        @RequestBody sseRequest: SseRequest
    ): ResponseEntity<Void> {
        sseEmitterManager.sendEventToClient(clientId, sseRequest.action)
        return ResponseEntity.noContent().build()
    }
}

data class SseRequest(
    val action: SseEvent
)