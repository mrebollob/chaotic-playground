package com.mrebollob.chaoticplayground.domain.repository

import com.mrebollob.chaoticplayground.domain.entity.House
import com.mrebollob.chaoticplayground.domain.exception.PlayGroundException
import com.mrebollob.chaoticplayground.domain.functional.Either

interface HouseRepository {

    suspend fun addHouse(house: House): Either<PlayGroundException, Unit>

    suspend fun getHouses(): Either<PlayGroundException, List<House>>

    suspend fun clearData()
}