package com.jvadev.phoneapi.controllers

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class RouteConfiguration {

    @Bean
    fun routes(phoneHandler: PhoneHandler) = coRouter {
        "/phone".nest {
            GET("/{deviceName}", phoneHandler::getPhoneByName)
            GET("/", phoneHandler::getPhones)
            POST("/", phoneHandler::addPhone)
            DELETE("/{deviceName}", phoneHandler::delete)
        }
    }
}