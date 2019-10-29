package com.mrebollob.chaoticplayground.domain.repository

import com.mrebollob.chaoticplayground.domain.entity.House
import com.mrebollob.chaoticplayground.domain.exception.PlayGroundException
import com.mrebollob.chaoticplayground.domain.functional.Either
import com.mrebollob.chaoticplayground.domain.functional.flatMap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HouseRepository @Inject constructor(
    private val houseGateway: HouseGateway,
    private val webScraper: WebScraper
) {
    suspend fun addHouse(house: House): Either<PlayGroundException, Unit> =
        houseGateway.addHouse(house)

    suspend fun addHouse(url: String): Either<PlayGroundException, Unit> {
        return withContext(Dispatchers.IO) {
            webScraper.getHouse(url)
                .flatMap { runBlocking { houseGateway.addHouse(it) } }
        }
    }

    suspend fun getHouses(): Either<PlayGroundException, List<House>> = houseGateway.getHouses()

    suspend fun clearData() = houseGateway.clearData()
}