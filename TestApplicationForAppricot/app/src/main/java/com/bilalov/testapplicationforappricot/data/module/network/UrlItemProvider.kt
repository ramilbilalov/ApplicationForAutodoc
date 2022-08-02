package com.bilalov.testapplicationforappricot.data.module.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object UrlItemProvider {

    private var retrofit: Retrofit? = null

    private const val url: String = "https://api.github.com/"

    @Provides
    fun baseUrl() = url

//    @Provides
//    @Singleton
//    fun provideRetrofit(baseUrl: String):ApiService =
//        Retrofit.Builder()
//            .baseUrl(baseUrl)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(ApiService::class.java)

    val retrofitInstance: Retrofit?
        get() {
            if (retrofit == null) {

                val httpLoggingInterceptor = HttpLoggingInterceptor()
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

                val okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(httpLoggingInterceptor)
                    .build()

                retrofit = Retrofit.Builder().baseUrl(baseUrl())
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create()).build()
            }
            return retrofit
        }
}