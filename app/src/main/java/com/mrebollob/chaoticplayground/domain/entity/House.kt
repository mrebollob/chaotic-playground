package com.mrebollob.chaoticplayground.domain.entity

import java.util.*

data class House(
    val name: String,
    val id: String = generateId()
)

private fun generateId(): String =
    "HOUSE_" + UUID.randomUUID().toString().replace("-".toRegex(), "")
