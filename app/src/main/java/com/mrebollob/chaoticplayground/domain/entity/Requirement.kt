package com.mrebollob.chaoticplayground.domain.entity

import com.mrebollob.chaoticplayground.domain.extension.generateId

data class Requirement(
    val id: String = generateId(),
    val title: String,
    val checked: Boolean = false
)