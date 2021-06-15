package com.example.pwameme.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
@Entity(tableName="comment")
data class Comment(
    val idMeme: String,
    val idUser: String,
    val commentText: String,
    val date: Long,
    val liked: List<String> = listOf(),
    val unliked: List<String> = listOf(),
    val liking: Boolean = false,
    val unliking: Boolean = false,
    @PrimaryKey(autoGenerate = false)
    val _id:String= UUID.randomUUID().toString()
)