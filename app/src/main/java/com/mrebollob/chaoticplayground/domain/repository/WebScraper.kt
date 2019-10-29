package com.mrebollob.chaoticplayground.domain.repository

import com.mrebollob.chaoticplayground.domain.entity.House
import com.mrebollob.chaoticplayground.domain.exception.PlayGroundException
import com.mrebollob.chaoticplayground.domain.functional.Either

interface WebScraper {

    fun getHouse(url: String): Either<PlayGroundException, House>
}