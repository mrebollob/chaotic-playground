package com.mrebollob.chaoticplayground.domain.interactor

import com.mrebollob.chaoticplayground.domain.entity.MarvelComic
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SortComicsByPriceUseCase @Inject constructor() {

    fun sort(sortType: SortType, comics: List<MarvelComic>): List<MarvelComic> {
        return when (sortType) {
            SortType.LOW_TO_HIGH -> comics
                .sortedBy { it.printPrice }
            SortType.HIGH_TO_LOW, SortType.UNKNOWN -> comics
                .sortedByDescending { it.printPrice }
        }
    }
}

enum class SortType {
    LOW_TO_HIGH,
    HIGH_TO_LOW,
    UNKNOWN,
}

fun SortType.switch(): SortType {
    return when (this) {
        SortType.LOW_TO_HIGH -> SortType.HIGH_TO_LOW
        else -> SortType.LOW_TO_HIGH
    }
}

