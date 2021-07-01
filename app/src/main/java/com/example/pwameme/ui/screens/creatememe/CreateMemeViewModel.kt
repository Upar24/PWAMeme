package com.example.pwameme.ui.screens.creatememe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pwameme.data.local.entities.Meme
import com.example.pwameme.data.local.entities.User
import com.example.pwameme.repository.MemeRepository
import com.example.pwameme.util.Constants.NO_USERNAME
import com.example.pwameme.util.Event
import com.example.pwameme.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val _userMeme = MutableLiveData<Event<Resource<List<Meme>>>>()
    val userMeme : LiveData<Event<Resource<List<Meme>>>> = _userMeme
    private val _userTrash = MutableLiveData<Event<Resource<List<Meme>>>>()
    val userTrash : LiveData<Event<Resource<List<Meme>>>> = _userTrash
    private val _likeStatus = MutableLiveData<Event<Resource<Boolean>>>()
    val likeStatus : LiveData<Event<Resource<Boolean>>> = _likeStatus
    private val _saveStatus = MutableLiveData<Event<Resource<Boolean>>>()
    val saveStatus : LiveData<Event<Resource<Boolean>>> = _saveStatus


    fun getUserMeme(username: String){
        _userMeme.postValue(Event(Resource.loading(null)))
        if(username == NO_USERNAME || username == ""){
            _userMeme.postValue(Event(Resource.error("Please Login First",null)))
            return
        }
        viewModelScope.launch {
            val result= repository.getUserMemes(username)
            _userMeme.postValue(Event(result))
        }
    }
    fun getUserTrash(username: String){
        _userTrash.postValue(Event(Resource.loading(null)))
        if(username == NO_USERNAME || username == ""){
            _userTrash.postValue(Event(Resource.error("Please Login First",null)))
            return
        }
        viewModelScope.launch {
            val result= repository.getUserTrash(username)
            _userTrash.postValue(Event(result))
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
    fun toggleLike(meme:Meme){
        _likeStatus.postValue(Event(Resource.loading(null)))
        viewModelScope.launch {
            val result = repository.toggleLike(meme)
            _likeStatus.postValue(Event(result))
        }
    }
    fun toggleSave(meme:Meme){
        _saveStatus.postValue(Event(Resource.loading(null)))
        viewModelScope.launch {
            val result= repository.toggleSave(meme)
            _saveStatus.postValue(Event(result))
        }
    }
}
