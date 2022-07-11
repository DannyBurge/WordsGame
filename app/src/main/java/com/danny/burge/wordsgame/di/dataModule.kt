package com.danny.burge.wordsgame.di

import com.danny.burge.wordsgame.di.model.PrefsHelper
import com.danny.burge.wordsgame.interaction.NetworkRepository
import com.danny.burge.wordsgame.logic.GameLogic
import org.koin.dsl.module

val dataModule = module {
    single {
        PrefsHelper(
            context = get()
        )
    }

    single {
        NetworkRepository(
            serverApi = get()
        )
    }

    single {
        GameLogic(
            context = get(),
            mainScreenViewModel = get(),
            settingsScreenViewModel = get()
        )
    }

    single {

    }
}