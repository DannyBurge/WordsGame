package com.danny.burge.wordsgame.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.danny.burge.wordsgame.constants.DATABASE_NAME

@Entity(tableName = "words_db")
data class Word(
    @PrimaryKey
    var word_letters: String = "",
    var word_size : Int = 0,
)