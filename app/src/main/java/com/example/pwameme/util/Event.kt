package com.example.pwameme.util

open class Event <out T>(private val content: T){
    private var hasBeenHandled = false
        private set

    fun getContentIfNotHandled()= if(hasBeenHandled){
        null
    }else{
        hasBeenHandled = true
        content
    }
    fun peekContent() = content
}