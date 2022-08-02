package com.bilalov.testapplicationforappricot.data.module.network

import com.bilalov.testapplicationforappricot.data.request.searchRepo.userProfile.ResponseUserProfile
import com.bilalov.testapplicationforappricot.data.request.ResponseSearchRepos
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/repositories?")
    @Headers("Accept: application/vnd.github+json", "Authorization: token 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")
     fun getQuestList(@Query("q") textInputSearch:String ): Call<ResponseSearchRepos>

    @GET("users/{login}")
    @Headers("Accept: application/vnd.github+json", "Authorization: token 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")
    fun getQuestListProfile(@Path("login") login:String ): Call<ResponseUserProfile>

}
