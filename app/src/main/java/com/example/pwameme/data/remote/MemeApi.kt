package com.example.pwameme.data.remote

import com.example.pwameme.data.local.entities.Meme
import com.example.pwameme.data.local.entities.User
import com.example.pwameme.data.remote.requests.AccountRequest
import com.example.pwameme.data.remote.requests.SearchRequest
import com.example.pwameme.data.remote.responses.SimpleResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MemeApi {

    @POST("/register")
    suspend fun register(
        @Body registerRequest: AccountRequest
    ): Response<SimpleResponse>

    @POST("/login")
    suspend fun login(
        @Body loginRequest: AccountRequest
    ):Response<SimpleResponse>

    @POST("/getuserinfo")
    suspend fun getUserInfo():Response<User>

    @POST("/updateuserinfo")
    suspend fun updateUserInfo(@Body user: User):Response<ResponseBody>

    @POST("/savetrash")
        suspend fun saveTrash(
            @Body meme: Meme
        ):Response<ResponseBody>

    @POST("/savememe")
    suspend fun saveMeme(
        @Body meme: Meme
    ):Response<ResponseBody>

    @GET("/getallmemes")
    suspend fun getAllMemes():Response<ResponseBody>

}