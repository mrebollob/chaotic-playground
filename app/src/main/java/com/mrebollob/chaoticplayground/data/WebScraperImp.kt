package com.mrebollob.chaoticplayground.data

import com.mrebollob.chaoticplayground.domain.entity.House
import com.mrebollob.chaoticplayground.domain.exception.PlayGroundException
import com.mrebollob.chaoticplayground.domain.functional.Either
import com.mrebollob.chaoticplayground.domain.repository.WebScraper
import org.jsoup.Jsoup

class WebScraperImp : WebScraper {

    override fun getHouse(url: String): Either<PlayGroundException, House> {
        return try {
            val doc = Jsoup.connect(url).get()

            val imageUrl = doc
                .select("main-image_first")
                .first()
                .select("a")
                .attr("src")


            Either.Right(
                House(
                    name = "",
                    imageUrl = "",
                    rentPrice = 0.toFloat()
                )
            )

        } catch (e: Exception) {
            Either.Left(PlayGroundException("Error"))

        }
    }
}