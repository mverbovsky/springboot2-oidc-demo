package com.github.mverbovsky.demo.oidc.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority
import java.util.stream.Collectors

@Configuration
class SecurityConfig : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http.oauth2Login()
                .loginPage("/oauth2/authorization/demo")
                .userInfoEndpoint()
                    .userAuthoritiesMapper(userAuthoritiesMapper())
                    .and()
                .and()
            .authorizeRequests()
                .anyRequest()
                    .authenticated()
    }

    private fun userAuthoritiesMapper(): GrantedAuthoritiesMapper {
        return GrantedAuthoritiesMapper { authorities: Collection<GrantedAuthority> ->
            var mappedAuthorities: Collection<GrantedAuthority>? = null
            for (authority in authorities) {
                if (authority is OidcUserAuthority) {
                    val token = authority.idToken
                    val roles = token.getClaimAsStringList("roles")
                    if (roles != null) {
                        mappedAuthorities = roles.stream()
                            .map { role: String -> role.toUpperCase() }
                            .map { roleUpperCase: String -> "ROLE_$roleUpperCase" }
                            .map { role: String -> SimpleGrantedAuthority(role) }
                            .collect(Collectors.toSet<GrantedAuthority>())
                    }
                }
            }
            mappedAuthorities ?: authorities
        }
    }
}
