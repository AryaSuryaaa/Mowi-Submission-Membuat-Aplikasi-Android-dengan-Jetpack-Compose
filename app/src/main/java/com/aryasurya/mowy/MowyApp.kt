package com.aryasurya.mowy

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.aryasurya.mowy.ui.navigation.NavigationItem
import com.aryasurya.mowy.ui.navigation.Screen
import com.aryasurya.mowy.ui.screen.detail.DetailScreen
import com.aryasurya.mowy.ui.screen.home.HomeScreen

@Composable
fun MowyApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.DetailMovie.route) {
                BottomBar(navController = navController)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController ,
            startDestination = Screen.Home.route ,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = { movieId ->
                        navController.navigate(Screen.DetailMovie.createRoute(movieId = movieId))
                    }
                )
            }
            composable(Screen.Favorite.route) {}
            composable(Screen.Profile.route) {}
            composable(
                Screen.DetailMovie.route ,
                arguments = listOf(navArgument("movieId") { type = NavType.LongType })
            ) {
                val id = it.arguments?.getLong("movieId") ?: -1L
                DetailScreen(
                    movieId = id,
                    navigateBack = {
                        navController.navigateUp()
                    },
                )
            }
        }
    }
}

@Composable
private fun BottomBar(
    navController: NavHostController ,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentState = navBackStackEntry?.destination?.route

        val navigationItems = listOf(
            NavigationItem(
                title = "Home" ,
                icon = Icons.Default.Home,
                screen = Screen.Home
            ) ,
            NavigationItem(
                title = "Favorite" ,
                icon = Icons.Default.ShoppingCart,
                screen = Screen.Favorite
            ),
            NavigationItem(
                title = "Profile" ,
                icon = Icons.Default.AccountCircle,
                screen = Screen.Profile
            )
        )
        navigationItems.map { item ->
            NavigationBarItem(
                selected = currentState == item.screen.route ,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                } ,
                icon = {
                    Icon(
                        imageVector = item.icon ,
                        contentDescription = item.title ,
                    )
                } ,
                label = { Text(text = item.title) } ,
            )
        }
    }
}
