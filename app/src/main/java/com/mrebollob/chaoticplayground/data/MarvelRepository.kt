package com.mrebollob.chaoticplayground.data

class MarvelRepository(private val marvelService: MarvelService) {
    suspend fun getTodo(id: Int) = marvelService.getTodo(id)
}