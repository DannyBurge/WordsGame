package com.danny.burge.wordsgame.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words_db")
data class Word(
    @PrimaryKey
    val word_letters: String,
    val word_size : Int,
)