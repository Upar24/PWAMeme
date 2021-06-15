package com.example.pwameme.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pwameme.data.local.entities.Meme
import kotlinx.coroutines.flow.Flow

@Dao
interface MemeDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(meme: Meme)

    @Query("SELECT * FROM meme WHERE _id= :postID")
    fun observeMemeById(postID: String): LiveData<Meme>

    @Query("SELECT * FROM meme WHERE _id = :postID")
    suspend fun getPostById(postID: String): Meme?

    @Query("SELECT * FROM meme ORDER BY date DESC")
    fun getAllPosts(): Flow<List<Meme>>
}
