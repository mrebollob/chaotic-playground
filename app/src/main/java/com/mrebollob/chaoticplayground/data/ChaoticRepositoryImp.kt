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

package com.mrebollob.chaoticplayground.data

import com.google.gson.Gson
import com.mrebollob.chaoticplayground.data.model.ChaoticApiError
import com.mrebollob.chaoticplayground.domain.entity.Requirement
import com.mrebollob.chaoticplayground.domain.exception.PlayGroundException
import com.mrebollob.chaoticplayground.domain.functional.Either
import com.mrebollob.chaoticplayground.domain.repository.ChaoticRepository
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber
import java.io.IOException

class ChaoticRepositoryImp(private val chaoticService: ChaoticService) :
    ChaoticRepository {

    override suspend fun getRequirements(): Either<PlayGroundException, List<Requirement>> {
        return try {
            Either.Right(chaoticService.getRequirements().map { it.toRequirement() })
        } catch (httpException: HttpException) {
            Either.Left(parseError(httpException.response()))
        } catch (e: IOException) {
            Either.Left(PlayGroundException("Error"))
        }
    }

    private fun parseError(response: Response<*>?): PlayGroundException {
        val gson = Gson()
        return try {
            val errorBody = response?.errorBody()?.string() ?: "{}"

            val marvelError =
                gson.fromJson<ChaoticApiError>(errorBody, ChaoticApiError::class.java)

            PlayGroundException(marvelError.code ?: "Null error code", null)
        } catch (e: IOException) {
            Timber.e(e)
            PlayGroundException("Null error code", e)
        }
    }
}