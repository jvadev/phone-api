package com.jvadev.phoneapi.controllers

import com.jvadev.phoneapi.AbstractIT
import com.jvadev.phoneapi.models.Phone
import com.jvadev.phoneapi.repositories.PhoneRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.body

@AutoConfigureWebTestClient
class RouteConfigurationTest(
    private val webTestClient: WebTestClient,
    private val repository: PhoneRepository
) : AbstractIT() {
    init {
        beforeEach {
            coroutineScope {
                repository.saveAll(
                    flowOf(
                        Phone(
                            deviceName = "Iphone12 Pro Max",
                            bookedBy = "Daniil Zverev",
                            available = false
                        ),
                        Phone(
                            deviceName = "Samsung Fold 2",
                            bookedBy = "Remy Danton",
                            available = false
                        ),
                        Phone(
                            deviceName = "Iphone11 Pro",
                            bookedBy = "Ian McSheen",
                            available = false
                        )
                    )
                ).collect()
            }
        }

        afterEach {
            coroutineScope {
                repository.deleteAll()
            }
        }

        test("return phone by device name") {
            coroutineScope {
                webTestClient
                    .get()
                    .uri("/phone/Iphone12 Pro Max")
                    .exchange()
                    .expectStatus()
                    .isOk
                    .expectBody()
                    .json(
                        """
                        {
                        "deviceName": "Iphone12 Pro Max",
                        "bookedBy":  "Daniil Zverev",
                        "available":  false
                        }
                    """.trimIndent()
                    )
            }
        }

        test("return list of phones") {
            coroutineScope {
                webTestClient
                    .get()
                    .uri("/phone/")
                    .exchange()
                    .expectStatus()
                    .isOk
                    .expectBody()
                    .json(
                        """
                    [
                        {
                        "deviceName": "Iphone12 Pro Max",
                        "bookedBy":  "Daniil Zverev",
                        "available":  false
                        },
                        {
                        "deviceName": "Samsung Fold 2",
                        "bookedBy":  "Remy Danton",
                        "available":  false
                        },
                        {
                        "deviceName": "Iphone11 Pro",
                        "bookedBy":  "Ian McSheen",
                        "available":  false
                        }
                    ]
                    """.trimIndent()
                    )
            }
        }

        test("add new phone") {
            webTestClient
                .post()
                .uri("/phone/")
                .bodyValue(
                    Phone(
                        deviceName = "Iphone12 Pro Max",
                        bookedBy = "Daniil Zverev"
                    )
                )
                .exchange()
                .expectStatus()
                .isCreated
        }

        test("delete phone") {
            webTestClient
                .delete()
                .uri("/phone/Iphone12 Pro Max")
                .exchange()
                .expectStatus()
                .isOk
        }
    }
}
