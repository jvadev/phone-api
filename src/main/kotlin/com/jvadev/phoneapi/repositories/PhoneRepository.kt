package com.jvadev.phoneapi.repositories

import com.jvadev.phoneapi.models.Phone
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface PhoneRepository: CoroutineCrudRepository<Phone, String> {

   suspend fun findPhoneByDeviceName(deviceName: String): Phone?
}