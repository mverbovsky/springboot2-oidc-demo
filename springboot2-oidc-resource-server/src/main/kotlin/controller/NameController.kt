package com.github.mverbovsky.demo.oidc.controller

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class NameController {

    @GetMapping("/api/name")
    fun name(@AuthenticationPrincipal jwt: Jwt): String {
        return jwt.let {
            val givenName = it.getClaimAsString("given_name")
            val familyName = it.getClaimAsString("family_name")
            val preferredUsername = it.getClaimAsString("preferred_username")
            "$givenName $familyName ($preferredUsername)"
        }
    }
}
