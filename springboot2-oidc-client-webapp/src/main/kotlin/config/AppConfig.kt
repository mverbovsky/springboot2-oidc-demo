package com.github.mverbovsky.demo.oidc.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class AppConfig {

    @Bean
    fun webClient(
        clientRegistrations: ClientRegistrationRepository,
        authorizedClients: OAuth2AuthorizedClientRepository
    ): WebClient {
        val oauth2 = ServletOAuth2AuthorizedClientExchangeFilterFunction(clientRegistrations, authorizedClients)
        oauth2.setDefaultOAuth2AuthorizedClient(true)
        return WebClient.builder()
            .apply(oauth2.oauth2Configuration())
            .build()
    }
}
