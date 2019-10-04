/*
 *   Copyright (C) 2019 mrebollob.
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package com.mrebollob.chaoticplayground.data.model

import com.google.gson.annotations.SerializedName
import com.mrebollob.chaoticplayground.domain.entity.MarvelComic

data class MarvelApiComic(
    @SerializedName("id") private val id: String?,
    @SerializedName("title") private val title: String?,
    @SerializedName("description") private val description: String?,
    @SerializedName("pageCount") private val pageCount: Int?,
    @SerializedName("prices") private val prices: List<MarvelApiPrice>?,
    @SerializedName("thumbnail") private val thumbnail: MarvelApiImage?
) {
    fun toMarvelComic(): MarvelComic {
        return MarvelComic(
            id = id ?: "",
            title = title ?: "",
            description = description ?: "",
            pageCount = pageCount ?: -1,
            price = prices?.firstOrNull { it.type == "printPrice" }?.price ?: 0.toFloat(),
            thumbnail = thumbnail?.toImageUrl() ?: ""
        )
    }
}