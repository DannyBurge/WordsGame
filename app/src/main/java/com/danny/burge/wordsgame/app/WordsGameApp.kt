package com.danny.burge.wordsgame.app

import android.app.Application
import androidx.room.Room
import com.danny.burge.wordsgame.database.WordsDatabase
import com.danny.burge.wordsgame.di.appComponent
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WordsGameApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
        initDataBase()
    }

    private fun initKoin() {
        startKoin {
            androidContext(applicationContext)
            modules(appComponent)
        }
    }

    private fun initDataBase() {
        wordsDatabase = Room.databaseBuilder(this, WordsDatabase::class.java, "words.db")
            .createFromAsset("databases/words.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    companion object {
        var wordsDatabase: WordsDatabase? = null
        var state = AppGameData()
        val settings = AppSettings()
    }
}