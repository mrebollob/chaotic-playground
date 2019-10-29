package com.mrebollob.chaoticplayground.domain.entity

import java.util.*

data class House(
    val id: String = generateId(),
    val name: String,
    val imageUrl: String,
    val rentPrice: Float,
    val updated: Date = Date()
)

private fun generateId(): String =
    "HOUSE_" + UUID.randomUUID().toString().replace("-".toRegex(), "")
