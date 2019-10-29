package com.mrebollob.chaoticplayground.data.model

import com.google.gson.annotations.SerializedName
import com.mrebollob.chaoticplayground.data.USER_ID_FIELD
import com.mrebollob.chaoticplayground.domain.entity.House
import java.util.*

data class HouseModel(
    val id: String? = null,
    @SerializedName(USER_ID_FIELD)
    val userId: String? = null,
    val name: String? = null,
    val created: Date? = null
) {

    fun toHouse(): House {
        return House(
            id = id ?: "",
            name = name ?: ""
        )
    }
}

fun House.toHouseModel(userId: String) =
    HouseModel(
        id = id,
        userId = userId,
        name = name,
        created = Date()
    )