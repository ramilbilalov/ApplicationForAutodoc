package com.bilalov.testapplicationforappricot.data.module.network

import com.bilalov.testapplicationforappricot.data.request.ResponseSearchRepos
import com.bilalov.testapplicationforappricot.data.request.searchRepo.userProfile.ResponseUserProfile
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/repositories?")
     fun getQuestList(
        @Header("Accept")headerFirst: String,
        @Header("Authorization")decoded: String,
        @Query("q") textInputSearch: String
    ): Call<ResponseSearchRepos>

    @GET("users/{login}")
    fun getQuestListProfile(
        @Header("Accept")headerFirst: String,
        @Header("Authorization")decoded: String,
        @Path("login") login:String
    ): Call<ResponseUserProfile>

}
