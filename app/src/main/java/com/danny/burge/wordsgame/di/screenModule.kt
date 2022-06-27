package com.danny.burge.wordsgame.di

import com.danny.burge.wordsgame.ui.main.MainActivityViewModel
import com.danny.burge.wordsgame.ui.screens.main.MainScreenViewModel
import com.danny.burge.wordsgame.ui.screens.settings.SettingsScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val screenModule = module {
    viewModel { MainActivityViewModel() }
    viewModel { MainScreenViewModel() }
    viewModel { SettingsScreenViewModel(context = get()) }
}