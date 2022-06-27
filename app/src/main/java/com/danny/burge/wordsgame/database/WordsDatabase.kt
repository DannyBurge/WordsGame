package com.danny.burge.wordsgame.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.danny.burge.wordsgame.database.models.Word

@Database(entities = [Word::class], version = 1, exportSchema = true)
abstract class WordsDatabase : RoomDatabase() {
    abstract fun wordDAO(): WordDAO
}