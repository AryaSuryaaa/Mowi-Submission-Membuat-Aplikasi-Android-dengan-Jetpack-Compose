package com.aryasurya.mowy.ui.navigation

sealed class Screen(val route: String) {
    object Home: Screen("home")
    object Favorite: Screen("favorite")
    object Profile: Screen("profile")
    object DetailMovie: Screen("home/{movieId}") {
        fun createRoute(movieId: Long) = "home/$movieId"
    }
    object Search: Screen("search")
    object DetailMovieSearch : Screen("search/{movieId}") {
        fun createRoute(movieId: Long) = "search/$movieId"
    }

}
