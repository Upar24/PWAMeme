package com.example.pwameme.repository

import android.app.Application
import com.example.pwameme.data.local.MemeDao
import com.example.pwameme.data.local.entities.User
import com.example.pwameme.data.remote.MemeApi
import com.example.pwameme.data.remote.requests.AccountRequest
import com.example.pwameme.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MemeRepository @Inject constructor(
    private val memeDao: MemeDao,
    private val memeApi: MemeApi,
    private val context: Application
) {
    suspend fun login(username: String, password: String) = withContext(Dispatchers.IO) {
        try {
            val response = memeApi.login(AccountRequest(username, password))
            if (response.isSuccessful && response.body()!!.successful) {
                Resource.success(response.body()?.message)
            } else {
                Resource.error(response.body()?.message ?: response.message(), null)
            }
        } catch (e: Exception) {
            Resource.error("Couldnt connect to the server, check your internet connection", null)
        }
    }

    suspend fun register(username: String, password: String) = withContext(Dispatchers.IO) {
        try {
            val response = memeApi.register(AccountRequest(username, password))
            if (response.isSuccessful && response.body()!!.successful) {
                Resource.success(response.body()?.message)
            } else {
                Resource.error(response.body()?.message ?: response.message(), null)
            }
        } catch (e: Exception) {
            Resource.error("Couldnt connect to the server, check your internet connection", null)
        }
    }
    suspend fun getUserInfo() = withContext(Dispatchers.IO){
        try {
            val response = memeApi.getUserInfo()
            if(response.isSuccessful){
                Resource.success(response.body())
            }else{
                Resource.error(response.message(),null)
            }
        }catch(e:Exception){
            Resource.error("Couldnt connect to the server",null)
        }
    }
    suspend fun updateUserInfo(user: User) = withContext(Dispatchers.IO){
        try {
            val response = memeApi.updateUserInfo(user)
            if(response.isSuccessful){
                Resource.success(response.message())
            }else{
                Resource.error(response.message(),null)
            }
        }catch (e:Exception){
            Resource.error("Couldnt save the update profile, please try again", null)
        }
    }

    suspend fun getAllMemes() = withContext(Dispatchers.IO) {
        try {
            val response = memeApi.getAllMemes()

            if (response.isSuccessful) {
                Resource.success(response.body())
            } else {
                Resource.error(response.message(), null)
            }
        }catch (e: Exception) {
            Resource.error("Couldnt connect to the server, check your internet connection", null)
        }

    }
}