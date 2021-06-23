package com.example.pwameme.ui.screens.auth

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pwameme.data.remote.BasicAuthInterceptor
import com.example.pwameme.repository.MemeRepository
import com.example.pwameme.util.Constants.KEY_LOGGED_IN_PASSWORD
import com.example.pwameme.util.Constants.KEY_LOGGED_IN_USERNAME
import com.example.pwameme.util.Constants.LOGIN
import com.example.pwameme.util.Constants.LOGOUT
import com.example.pwameme.util.Constants.NO_PASSWORD
import com.example.pwameme.util.Constants.NO_USERNAME
import com.example.pwameme.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: MemeRepository
): ViewModel(){
    @Inject
    lateinit var sharedPref: SharedPreferences
    @Inject
    lateinit var basicAuthInterceptor : BasicAuthInterceptor
    var usernamevm: String? = null
    var passwordvm: String? = null
    var photouservm: String? = null
    private val _desc=MutableLiveData<String>()
    var desc: LiveData<String> = _desc
    fun getDesc(username:String){
        _desc.value = username
    }

    private val _registerStatus = MutableLiveData<Resource<String>>()
    val registerStatus : LiveData<Resource<String>> = _registerStatus
    private val _loginStatus = MutableLiveData<Resource<String>>()
    val loginStatus : LiveData<Resource<String>> = _loginStatus

    fun loginUser(username:String,password:String){
        _loginStatus.postValue(Resource.loading(null))
        if(username.isEmpty() || password.isEmpty()){
            _loginStatus.postValue(Resource.error("Please fill out all the fields",null))
            return
        }
        viewModelScope.launch {
            usernamevm=username
            passwordvm=password
            val result= repository.login(username,password)
            _loginStatus.postValue(result)
        }
    }
    fun registerUser(username: String,password: String,repeatedPassword:String){
        _registerStatus.postValue(Resource.loading(null))
        if(username.isEmpty() || password.isEmpty() || repeatedPassword.isEmpty()){
            _registerStatus.postValue(Resource.error("Please fill out all the fields",null))
            return
        }
        if(password != repeatedPassword){
            _registerStatus.postValue(Resource.error("The passwords do not match",null))
            return
        }
        viewModelScope.launch {
            val result= repository.register(username,password)
            _registerStatus.postValue(result)
        }
    }
    fun isLoggedIn():Boolean{
        usernamevm=sharedPref.getString(KEY_LOGGED_IN_USERNAME,NO_USERNAME) ?: NO_USERNAME
        passwordvm=sharedPref.getString(KEY_LOGGED_IN_PASSWORD, NO_PASSWORD) ?: NO_PASSWORD
        return usernamevm != NO_USERNAME && passwordvm != NO_PASSWORD
    }
    fun authenticateApi(username:String,password:String){
        basicAuthInterceptor.username = username
        basicAuthInterceptor.password = password
    }
}















