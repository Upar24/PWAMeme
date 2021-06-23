package com.example.pwameme.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
@Entity(tableName = "meme")
data class Meme(
    val usernameKeyword: String ="",
    val keyword: List<String> = listOf(),
    val type:String = "",
    val usernameAuthor: String= "",
    val imageMeme: String?= null,
    val descMeme: String= "",
    val liked: List<String> = listOf(),
    val unliked: List<String> = listOf(),
    val comments: List<String> = listOf(),
    val saved: List<String> = listOf(),
    val date: Long = 0,
    val liking: Boolean = false,
    val unliking: Boolean = false,
    val saving: Boolean = false,
    @PrimaryKey(autoGenerate = false)
    val _id:String= UUID.randomUUID().toString()

)