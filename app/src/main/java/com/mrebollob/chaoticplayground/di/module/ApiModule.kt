package com.mrebollob.chaoticplayground.di.module


import android.app.Application
import com.mrebollob.chaoticplayground.BuildConfig
import com.mrebollob.chaoticplayground.data.MarvelRepository
import com.mrebollob.chaoticplayground.data.MarvelService
import com.mrebollob.chaoticplayground.di.annotation.BaseUrl
import dagger.Module
import dagger.Provides
import okhttp3.Cache
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
    fun provideLeitnerRepository(
        marvelService: MarvelService
    ): MarvelRepository = MarvelRepository(marvelService)

    @Provides
    @Singleton
    fun provideMarvelService(
        okHttpClient: OkHttpClient,
        @BaseUrl baseUrl: String
    ): MarvelService =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(MarvelService::class.java)

    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        context: Application
    ): OkHttpClient {

        val cacheSize = (5 * 1024 * 1024).toLong()
        val cache = Cache(context.cacheDir, cacheSize)

        return OkHttpClient.Builder().apply {
            connectTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
            cache(cache)
            if (BuildConfig.DEBUG) addInterceptor(httpLoggingInterceptor)
        }.build()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @BaseUrl
    fun provideBaseUrl(): String = BuildConfig.BASE_URL
}