package com.example.userbrowser

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {


    @Headers("Authorization: token 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")
    @GET("search/users")
    fun searchUser(
        @Query("q") username: String
    ): Call<ResponseGithub>


    @Headers("Authorization: token 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")
    @GET("users/{username}")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<ResponseGithub>


    @Headers("Authorization: token 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")
    @GET("users/{username}/followers")
    fun getUserFollower(
        @Path("username") username: String
    ): Call<ResponseGithub>


    @Headers("Authorization: token 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")
    @GET("users/{username}/following")
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<ResponseGithub>
}