package com.example.github_search.app.network.api

import com.example.github_search.BuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

// Вместо DI можно использовать Singleton объекты, но для этого реализовать потоковую синхронизацию
// https://proandroiddev.com/synchronization-and-thread-safety-techniques-in-java-and-kotlin-f63506370e6d
object GitHubServiceImpl {

    private val okHttpClient: OkHttpClient by lazy {
        val loggingLevel = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        val interceptor = HttpLoggingInterceptor().setLevel(loggingLevel)
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Volatile
    private var localInstance: GitHubService? = null

    // Вместо метода удобнее использовать свойство
    // Для того, чтобы оно работало как функция, нужно синхронизировать getter
    @OptIn(ExperimentalSerializationApi::class)
    @get:Synchronized
    val gitHubServiceImpl: GitHubService
        get() {
            return localInstance ?: Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
                .client(okHttpClient)
                .build()
                .create(GitHubService::class.java)
        }
}
