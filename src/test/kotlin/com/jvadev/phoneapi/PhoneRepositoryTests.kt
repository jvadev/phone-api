package com.jvadev.phoneapi

import com.jvadev.phoneapi.models.Phone
import com.jvadev.phoneapi.repositories.PhoneRepository
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf

class PhoneRepositoryTests(
    private val repository: PhoneRepository
) : AbstractIT() {
    init {

        beforeEach {
            coroutineScope {
                repository.saveAll(
                    flowOf(
                        Phone(
                            deviceName = "Iphone",
                            bookedBy = null
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

        test("should return phone by device name") {
            coroutineScope {
                repository.findPhoneByDeviceName("Iphone") shouldBe Phone(
                    deviceName = "Iphone",
                    bookedBy = null
                )
            }
        }
    }
}

