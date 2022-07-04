package com.danny.burge.wordsgame.di

import com.danny.burge.wordsgame.interaction.NetworkRepository
import org.koin.dsl.module

val dataModule = module {
    single { NetworkRepository(get()) }
}