package com.github.mverbovsky.demo.oidc.controller

import com.github.mverbovsky.demo.oidc.service.NameService
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import java.util.stream.Collectors

@Controller
class IndexController(val nameService: NameService) {

    @GetMapping("/")
    fun index(model: Model): String? {
        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication != null) {
            val name: String? = nameService.name()
            model.addAttribute("name", name ?: authentication.name)
            model.addAttribute("roles", authentication.authorities
                .stream()
                .map { obj: GrantedAuthority -> obj.authority }
                .collect(Collectors.joining(", ")))
        }
        return "index"
    }
}
