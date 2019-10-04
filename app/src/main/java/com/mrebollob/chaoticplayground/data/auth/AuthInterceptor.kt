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

package com.mrebollob.chaoticplayground.data.auth

import com.mrebollob.chaoticplayground.domain.exception.PlayGroundException
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber

// Source code from: https://github.com/Karumi/MarvelApiClientAndroid

private const val TIMESTAMP_KEY = "ts"
private const val HASH_KEY = "hash"
private const val APIKEY_KEY = "apikey"

class AuthInterceptor(
    private val publicKey: String,
    private val privateKey: String,
    private val timeProvider: TimeProvider
) : Interceptor {

    private val authHashGenerator = AuthHashGenerator()

    override fun intercept(chain: Interceptor.Chain): Response {
        val timestamp = timeProvider.getCurrentTimeMillis().toString()
        var hash: String? = null
        try {
            hash = authHashGenerator.generateHash(timestamp, publicKey, privateKey)
        } catch (e: PlayGroundException) {
            Timber.e(e, "generateHash exception")
        }

        var request = chain.request()
        val url = request.url
            .newBuilder()
            .addQueryParameter(TIMESTAMP_KEY, timestamp)
            .addQueryParameter(APIKEY_KEY, publicKey)
            .addQueryParameter(HASH_KEY, hash)
            .build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}