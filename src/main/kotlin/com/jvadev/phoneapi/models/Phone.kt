package com.jvadev.phoneapi.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Phone(
    @Id
    val deviceName: String,
    var bookedBy: String?,
    var available: Boolean = true
)
