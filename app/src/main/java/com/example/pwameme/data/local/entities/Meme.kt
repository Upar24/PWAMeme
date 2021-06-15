package com.example.pwameme.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
@Entity(tableName = "meme")
data class Meme(
    val imageKeyword: String? = null,
    val usernameKeyword: String? = null,
    val keyword: List<String> = listOf(),
    val type:String = "trash",
    val imageAuthor: String= "",
    val usernameAuthor: String= "",
    val imageMeme: String?= null,
    val descMeme: String?= null,
    val liked: List<String> = listOf(),
    val unliked: List<String> = listOf(),
    val comments: List<String> = listOf(),
    val saved: List<String> = listOf(),
    val date: Long? = null,
    val liking: Boolean = false,
    val unliking: Boolean = false,
    val saving: Boolean = false,
    @PrimaryKey(autoGenerate = false)
    val _id:String= UUID.randomUUID().toString()

)