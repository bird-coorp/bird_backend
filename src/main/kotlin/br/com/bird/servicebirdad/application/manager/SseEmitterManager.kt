package br.com.bird.servicebirdad.application.manager

import org.springframework.stereotype.Component
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.util.concurrent.ConcurrentHashMap

@Component
class SseEmitterManager {

    private val emitters: MutableMap<String, SseEmitter> = ConcurrentHashMap()

    fun addClient(clientId: String): SseEmitter {
        val emitter = SseEmitter(0L)
        emitters[clientId] = emitter

        emitter.onCompletion { emitters.remove(clientId) }
        emitter.onTimeout { emitters.remove(clientId) }
        emitter.onError { emitters.remove(clientId) }

        return emitter
    }

    fun sendEventToClient(client: String, data: SseEvent) {
        emitters[client]?.let {
            try {
                it.send(SseEmitter.event().name("message").data(data))
            } catch (e: Exception) {
                emitters.remove(client)
            }
        }
    }

    fun log() {
        emitters.forEach(::println)
    }
}

enum class SseEvent {
    UPDATE_TIMELINE,
    SHUTDOWN_TIMELINE
}