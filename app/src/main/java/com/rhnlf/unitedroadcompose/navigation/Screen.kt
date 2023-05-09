package com.rhnlf.unitedroadcompose.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Profile : Screen("profile")
    object DetailPlayer : Screen("home/{playerId}") {
        fun createRoute(playerId: Int) = "home/$playerId"
    }
}