package com.example.pwameme.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pwameme.data.local.entities.Meme

@Database(
    entities = [Meme::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MemeDatabase : RoomDatabase(){
    abstract fun memeDao() : MemeDao
}