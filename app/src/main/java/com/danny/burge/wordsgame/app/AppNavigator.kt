package com.danny.burge.wordsgame.app

import androidx.navigation.NavController

object AppNavigator {

    fun navigate(controller: NavController, command: NavigationCommand) {
        when (command) {
            is NavigationCommand.ByRoute -> controller.navigate(command.route)
            NavigationCommand.Back -> controller.navigateUp()
        }
    }
}