package com.github.mverbovsky.demo.oidc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Springboot2OidcResourceServer

fun main(args: Array<String>) {
    runApplication<Springboot2OidcResourceServer>(*args)
}
