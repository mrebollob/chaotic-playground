package com.mrebollob.chaoticplayground.data.model

import com.google.gson.annotations.SerializedName
import com.mrebollob.chaoticplayground.data.USER_ID_FIELD
import com.mrebollob.chaoticplayground.domain.entity.House
import java.util.*

private const val TEST_IMG =
    "https://specials-images.forbesimg.com/imageserve/1026205392/960x0.jpg?fit=scale"

data class HouseModel(
    val id: String? = null,
    @SerializedName(USER_ID_FIELD)
    val userId: String? = null,
    val title: String? = null,
    val imageUrl: String? = null,
    val rentPrice: Float? = null,
    val requirements: List<String>? = null,
    val updated: Date? = null
) {
    fun toHouse(): House {
        return House(
            id = id ?: "",
            title = title ?: "",
            imageUrl = imageUrl ?: TEST_IMG,
            rentPrice = rentPrice ?: 0.toFloat(),
            requirements = requirements ?: emptyList(),
            updated = updated ?: Date()
        )
    }
}

fun House.toHouseModel(userId: String) =
    HouseModel(
        id = id,
        userId = userId,
        title = title,
        rentPrice = rentPrice,
        requirements = requirements,
        updated = Date()
    )