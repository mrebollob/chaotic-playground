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

package com.mrebollob.chaoticplayground.di.module

import com.google.firebase.firestore.FirebaseFirestore
import com.mrebollob.chaoticplayground.BuildConfig
import com.mrebollob.chaoticplayground.data.ChaoticRepositoryImp
import com.mrebollob.chaoticplayground.data.ChaoticService
import com.mrebollob.chaoticplayground.data.FirestoreRepository
import com.mrebollob.chaoticplayground.data.WebScraperImp
import com.mrebollob.chaoticplayground.data.auth.SessionManager
import com.mrebollob.chaoticplayground.di.annotation.BaseUrl
import com.mrebollob.chaoticplayground.domain.repository.ChaoticRepository
import com.mrebollob.chaoticplayground.domain.repository.HouseRepository
import com.mrebollob.chaoticplayground.domain.repository.WebScraper
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideHouseGateway(
        sessionManager: SessionManager, db: FirebaseFirestore
    ): HouseRepository = FirestoreRepository(sessionManager, db)

    @Provides
    @Singleton
    fun provideWebScraper(): WebScraper = WebScraperImp()

    @Provides
    @Singleton
    fun provideChaoticRepository(chaoticService: ChaoticService): ChaoticRepository =
        ChaoticRepositoryImp(chaoticService)

    @Provides
    @Singleton
    fun provideChaoticService(
        okHttpClient: OkHttpClient,
        @BaseUrl baseUrl: String
    ): ChaoticService =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ChaoticService::class.java)

    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {

        return OkHttpClient.Builder().apply {
            connectTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            if (BuildConfig.DEBUG) addInterceptor(httpLoggingInterceptor)
        }.build()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        return httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @BaseUrl
    fun provideBaseUrl(): String = BuildConfig.BASE_URL
}