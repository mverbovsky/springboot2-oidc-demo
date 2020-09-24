package com.github.mverbovsky.demo.oidc.service

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class NameService(private val webClient: WebClient) {

    fun name(): String? {
        return try {
            webClient
                .get()
                .uri("http://localhost:8092/api/name")
                .retrieve()
                .bodyToMono(String::class.java)
                .block()
        } catch (e: Exception) {
            null
        }
    }
}
