package com.danny.burge.wordsgame.app

sealed class NavigationCommand {
    data class ByRoute(val route: String) : NavigationCommand()
    object Back : NavigationCommand()
}