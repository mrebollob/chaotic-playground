package com.mrebollob.chaoticplayground.data.model

import com.mrebollob.chaoticplayground.domain.entity.House


data class HouseModel(
    val id: String? = null,
    val name: String? = null
) {

    fun toHouse(): House {
        return House(
            id = id ?: "",
            name = name ?: ""
        )
    }
}