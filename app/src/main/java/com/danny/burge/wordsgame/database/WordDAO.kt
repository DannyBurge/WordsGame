package com.danny.burge.wordsgame.database

import androidx.room.*
import com.danny.burge.wordsgame.constants.DATABASE_NAME
import com.danny.burge.wordsgame.database.models.Word

@Dao
interface WordDAO {
    @Query("SELECT * FROM words_db")
    fun readAll(): List<Word>?

    @Query("SELECT * FROM words_db WHERE word_size = :size")
    fun readAllWithLength(size: Int): List<Word>?

    @Insert
    fun addWord(newWord: Word)
}