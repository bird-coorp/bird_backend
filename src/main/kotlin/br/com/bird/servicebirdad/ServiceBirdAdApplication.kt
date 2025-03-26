package br.com.bird.servicebirdad

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class ServiceBirdAdApplication

fun main(args: Array<String>) {
    runApplication<ServiceBirdAdApplication>(*args)
}
