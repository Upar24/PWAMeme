package com.example.pwameme.ui.screens.creatememe

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pwameme.data.local.entities.Meme
import com.example.pwameme.data.local.entities.User
import com.example.pwameme.repository.MemeRepository
import com.example.pwameme.util.Constants
import com.example.pwameme.util.Constants.NO_USERNAME
import com.example.pwameme.util.Event
import com.example.pwameme.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateMemeViewModel @Inject constructor(
    private val repository: MemeRepository
): ViewModel(){
    private val _transactionStatus = MutableLiveData<Event<Resource<String>>>()
    val transactionStatus : LiveData<Event<Resource<String>>> = _transactionStatus
    private val _randomStatus = MutableLiveData<Event<Resource<String>>>()
    val randomStatus : LiveData<Event<Resource<String>>> = _randomStatus
    private val _memeStatus = MutableLiveData<Event<Resource<List<Meme>>>>()
    val memeStatus : LiveData<Event<Resource<List<Meme>>>> = _memeStatus
    private val _trashStatus = MutableLiveData<Event<Resource<List<Meme>>>>()
    val trashStatus : LiveData<Event<Resource<List<Meme>>>> = _trashStatus
    private val _leaderList = MutableLiveData<Event<Resource<List<User>>>>()
    val leaderList : LiveData<Event<Resource<List<User>>>> = _leaderList
    private val _userPost = MutableLiveData<Event<Resource<List<Meme>>>>()
    val userPost : LiveData<Event<Resource<List<Meme>>>> = _userPost

    fun getUserPost(username: String){
        _userPost.postValue(Event(Resource.loading(null)))
        if(username == NO_USERNAME || username == ""){
            _userPost.postValue(Event(Resource.error("Please Login First",null)))
            return
        }
        viewModelScope.launch {
            val result= repository.getUserPost(username)
            _userPost.postValue(Event(result))
        }
    }

    fun loadLeader(){
        _leaderList.postValue(Event(Resource.loading(null)))
        viewModelScope.launch {
            val result  = repository.getLeaderboard()
            _leaderList.postValue(Event(result))
        }
    }
    fun loadAllMemes(){
        _memeStatus.postValue(Event(Resource.loading(null)))
        viewModelScope.launch {
            val result = repository.getAllMemes()
            _memeStatus.postValue(Event(result))
        }
    }
    fun loadAllTrash(){
        _trashStatus.postValue(Event(Resource.loading(null)))
        viewModelScope.launch {
            val result = repository.getAllTrash()
            _trashStatus.postValue(Event(result))
        }
    }

    fun saveMeme(meme:Meme){
        _transactionStatus.postValue(Event(Resource.loading(null)))
        viewModelScope.launch{
            val result = repository.saveMeme(meme)
            _transactionStatus.postValue(Event(result))
        }
    }
    fun decreaseScore(username:String, decreaseScore: Int){
        _randomStatus.postValue(Event(Resource.loading(null)))
        viewModelScope.launch {
            val result= repository.decreaseScore(username,decreaseScore)
            _randomStatus.postValue(Event(result))
        }
    }
}
