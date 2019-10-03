package com.mrebollob.chaoticplayground.data

import com.mrebollob.chaoticplayground.domain.Todo
import retrofit2.http.GET
import retrofit2.http.Path

interface MarvelService {

    @GET("/todos/{id}")
    suspend fun getTodo(@Path(value = "id") todoId: Int): Todo
}