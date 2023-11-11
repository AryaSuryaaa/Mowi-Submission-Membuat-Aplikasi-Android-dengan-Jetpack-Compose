package com.aryasurya.mowy

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
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
import com.aryasurya.mowy.ui.screen.search.SearchScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MowyApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute == Screen.Home.route || currentRoute == Screen.Favorite.route || currentRoute == Screen.Profile.route) {
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
            composable(Screen.Search.route) {
                SearchScreen(navigateBack = { navController.navigateUp() })
            }
        }
        if (currentRoute == Screen.Home.route) {
            MediumTopAppBar(
                title = {},
                navigationIcon = {
                },
                actions = {
                    IconButton(onClick = { navController.navigate(Screen.Search.route) }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "",
//                        tint = colorResource(R.color.text_primary)
                        )
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = colorResource(R.color.black_transparent),
//                titleContentColor = colorResource(R.color.text_primary)
                ),
                scrollBehavior = scrollBehavior
            )
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
