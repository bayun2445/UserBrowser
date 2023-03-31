package com.example.userbrowser.api

import com.example.userbrowser.ResponseDetail
import com.example.userbrowser.ResponseGithub
import com.example.userbrowser.UserItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun searchUser(
        @Query("q") username: String
    ): Call<ResponseGithub>

    @GET("users/{username}")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<ResponseDetail>


    @GET("users/{username}/followers")
    fun getUserFollower(
        @Path("username") username: String
    ): Call<List<UserItem>>

    @GET("users/{username}/following")
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<List<UserItem>>
}