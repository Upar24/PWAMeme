package com.example.pwameme.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
@Entity(tableName = "user")
data class User(
    val username: String,
    val password: String,
    val following: List<String> = listOf(),
    val followers: List<String> = listOf(),
    val image: String = "R.drawable.image0",
    val bio: String = "",
    val score: Int = 1000,
    @PrimaryKey(autoGenerate = false)
    val _id:String= UUID.randomUUID().toString()
)