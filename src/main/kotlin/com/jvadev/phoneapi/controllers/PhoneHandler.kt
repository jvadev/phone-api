package com.jvadev.phoneapi.controllers

import com.jvadev.phoneapi.services.PhoneService
import org.springframework.http.HttpStatus.CREATED
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.ServerResponse.*

@Component
class PhoneHandler(private val service: PhoneService) {
    suspend fun getPhoneByName(request: ServerRequest): ServerResponse =
        service.getPhoneByName(request.pathVariable("deviceName"))
            ?.let { ok().bodyValueAndAwait(it) }
            ?: notFound().buildAndAwait()

    suspend fun getPhones(request: ServerRequest): ServerResponse = ok().bodyAndAwait(service.getPhones())

    suspend fun addPhone(request: ServerRequest): ServerResponse =
        service.addPhone(request.awaitBody()).let { status(CREATED).buildAndAwait() }

    suspend fun delete(request: ServerRequest): ServerResponse =
        service.delete(request.pathVariable("deviceName")).let { ok().buildAndAwait() }
}