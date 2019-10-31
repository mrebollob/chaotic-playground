package com.mrebollob.chaoticplayground.domain.entity

import com.mrebollob.chaoticplayground.domain.extension.generateId
import java.util.*

data class House(
    val id: String = generateId(),
    val title: String,
    val imageUrl: String,
    val rentPrice: Float,
    val requirements: List<String>,
    val updated: Date = Date()
)
