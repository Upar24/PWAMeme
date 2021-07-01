package com.example.pwameme.ui.screens.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pwameme.data.local.entities.User
import com.example.pwameme.repository.MemeRepository
import com.example.pwameme.util.Constants.NO_USERNAME
import com.example.pwameme.util.Event
import com.example.pwameme.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: MemeRepository
):ViewModel(){
    private val _getUserInfo = MutableLiveData<Event<Resource<User>>>()
    val getUserInfo : LiveData<Event<Resource<User>>> = _getUserInfo
    private val _userInfoUpdate = MutableLiveData<Event<Resource<String>>>()
    val userInfoUpdate : LiveData<Event<Resource<String>>> = _userInfoUpdate

    fun getUserInfo(username:String){
        _getUserInfo.postValue(Event(Resource.loading(null)))
        if(username == NO_USERNAME || username == ""){
            _getUserInfo.postValue(Event(Resource.error("Please Login First",null)))
            return
        }
        viewModelScope.launch {
            val result= repository.getUserInfo()
            _getUserInfo.postValue(Event(result))
        }
    }
    fun UpdateUser(user: User) {
        _userInfoUpdate.postValue(Event(Resource.loading(null)))
        if(user.password.isEmpty()){
            _userInfoUpdate.postValue(Event(Resource.error("The password can not be empty",null)))
            return
        }
        viewModelScope.launch {
            val result = repository.updateUserInfo(user)
            _userInfoUpdate.postValue(Event(result))
        }
    }
}