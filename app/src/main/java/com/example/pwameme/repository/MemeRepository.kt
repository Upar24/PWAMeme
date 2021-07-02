package com.example.pwameme.repository

import android.app.Application
import com.example.pwameme.data.local.MemeDao
import com.example.pwameme.data.local.entities.Meme
import com.example.pwameme.data.local.entities.User
import com.example.pwameme.data.remote.MemeApi
import com.example.pwameme.data.remote.requests.AccountRequest
import com.example.pwameme.data.remote.requests.PointRequest
import com.example.pwameme.data.remote.requests.UserRequest
import com.example.pwameme.data.remote.responses.SimpleResponse
import com.example.pwameme.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MemeRepository @Inject constructor(
    private val memeDao: MemeDao,
    private val memeApi: MemeApi,
    private val context: Application
) {
    val errorMessage =Resource.error(" Repo Couldnt connect to the server, check your internet connection", null)
    suspend fun login(username: String, password: String) = withContext(Dispatchers.IO) {
        try {
            val response = memeApi.login(AccountRequest(username, password))
            if (response.isSuccessful && response.body()!!.successful) {
                Resource.success(response.body()?.message)
            } else {
                Resource.error(response.body()?.message ?: response.message(), null)
            }
        } catch (e: Exception) {
            errorMessage
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
            errorMessage
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
            errorMessage
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
            errorMessage
        }
    }
    suspend fun decreaseScore(username: String, scoreDecrease: Int) = withContext(Dispatchers.IO){
        try {
            val response = memeApi.decreaseScore(PointRequest(username,scoreDecrease))
            if(response.isSuccessful){
                Resource.success(response.message() ?: "Score used successfully")
            }else{
                Resource.error(response.message() ?: "Check your connection",null)
            }
        }catch (e:Exception){
            errorMessage
        }
    }
    suspend fun saveMeme(meme: Meme) = withContext(Dispatchers.IO){
         try {
             val response= memeApi.saveMeme(meme)
             if(response.isSuccessful){
                 Resource.success(response.message() ?: "Successfully saved")
             }else{
                 Resource.error(response.message() ?: "Check your connection",null)
             }
         }catch (e: Exception){
             errorMessage
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
            errorMessage
        }

    }
    suspend fun getAllTrash()= withContext(Dispatchers.IO){
        try {
            val response = memeApi.getAllTrash()
            if(response.isSuccessful){
                Resource.success(response.body())
            }else{
                Resource.error(response.message(),null)
            }
        }catch (e: Exception){
            errorMessage
        }
    }
    suspend fun getLeaderboard()= withContext(Dispatchers.IO){
        try{
            val response = memeApi.getLeaderboard()
            if(response.isSuccessful){
                Resource.success(response.body())
            }else{
                Resource.error(response.message(),null)
            }
        }catch (e:Exception){
            errorMessage
        }
    }

    suspend fun getUserMemes(username: String)= withContext(Dispatchers.IO){
        try{
            val response = memeApi.getUserMemes(UserRequest(username))
            if(response.isSuccessful){
                Resource.success(response.body())
            }else{
                Resource.error(response.message(),null)
            }
        }catch (e:Exception){
            errorMessage
        }
    }

    suspend fun getUserTrash(username: String)= withContext(Dispatchers.IO){
        try{
            val response = memeApi.getUserTrash(UserRequest(username))
            if(response.isSuccessful){
                Resource.success(response.body())
            }else{
                Resource.error(response.message(),null)
            }
        }catch (e:Exception){
            errorMessage
        }
    }
    suspend fun toggleLike(meme:Meme)= withContext(Dispatchers.IO){
        try {
            val response = memeApi.toggleLike(meme)
            if(response.isSuccessful){
                Resource.success(response.body())
            }else{
                Resource.error(response.message() ,null)
            }
        }catch (e: Exception){
            errorMessage
        }
    }
    suspend fun toggleSave(meme:Meme)= withContext(Dispatchers.IO){
        try {
            val response = memeApi.toggleSave(meme)
            if(response.isSuccessful){
                Resource.success(response.body())
            }else{
                Resource.error(response.message() ?: "Check your connection",null)
            }
        }catch (e: Exception){
            errorMessage
        }
    }
}