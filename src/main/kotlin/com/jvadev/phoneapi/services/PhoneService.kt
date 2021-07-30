package com.jvadev.phoneapi.services

import com.jvadev.phoneapi.models.Phone
import com.jvadev.phoneapi.repositories.PhoneRepository
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Component

@Component
class PhoneService(
    private val repository: PhoneRepository
) {
    suspend fun getPhoneByName(deviceName: String): Phone? = repository.findPhoneByDeviceName(deviceName)

    fun getPhones(): Flow<Phone> = repository.findAll()

    suspend fun addPhone(phone: Phone) {
        repository.save(phone)
    }

    suspend fun delete(deviceName: String) {
        repository.deleteById(deviceName)
    }
}