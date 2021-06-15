package com.example.pwameme.repository

import android.app.Application
import com.example.pwameme.data.local.MemeDao
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
    suspend fun login(email: String, password: String) = withContext(Dispatchers.IO) {
        try {
            val response = memeApi.login(AccountRequest(email, password))
            if (response.isSuccessful && response.body()!!.successful) {
                Resource.success(response.body()?.message)
            } else {
                Resource.error(response.body()?.message ?: response.message(), null)
            }
        } catch (e: Exception) {
            Resource.error("Couldnt connect to the server, check your internet connection", null)
        }
    }

    suspend fun register(email: String, password: String) = withContext(Dispatchers.IO) {
        try {
            val response = memeApi.register(AccountRequest(email, password))
            if (response.isSuccessful && response.body()!!.successful) {
                Resource.success(response.body()?.message)
            } else {
                Resource.error(response.body()?.message ?: response.message(), null)
            }
        } catch (e: Exception) {
            Resource.error("Couldnt connect to the server, check your internet connection", null)
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