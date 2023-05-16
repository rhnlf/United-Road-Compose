package com.rhnlf.unitedroadcompose

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rhnlf.unitedroadcompose.navigation.NavigationItem
import com.rhnlf.unitedroadcompose.navigation.Screen
import com.rhnlf.unitedroadcompose.ui.screen.detail.DetailScreen
import com.rhnlf.unitedroadcompose.ui.screen.home.HomeScreen
import com.rhnlf.unitedroadcompose.ui.screen.profile.ProfileScreen
import com.rhnlf.unitedroadcompose.ui.theme.UnitedRoadTheme

@Composable
fun UnitedRoadApp(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.DetailPlayer.route) {
                BottomBar(navController)
            }
        }, modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(navigateToDetail = { playerId ->
                    navController.navigate(Screen.DetailPlayer.createRoute(playerId))
                })
            }
            composable(Screen.Profile.route) {
                ProfileScreen()
            }
            composable(
                route = Screen.DetailPlayer.route,
                arguments = listOf(navArgument("playerId") { type = NavType.IntType }),
            ) {
                val id = it.arguments?.getInt("playerId") ?: -1
                DetailScreen(
                    playerId = id,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UnitedRoadAppPreview() {
    UnitedRoadTheme {
        UnitedRoadApp()
    }
}

@Composable
private fun BottomBar(
    navController: NavHostController, modifier: Modifier = Modifier
) {
    BottomNavigation(
        modifier = modifier
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.home_page),
                icon = Icons.Default.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(R.string.profile_page),
                icon = Icons.Default.AccountCircle,
                screen = Screen.Profile,
            ),
        )
        BottomNavigation {
            navigationItems.map { item ->
                BottomNavigationItem(icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = if (item.title == stringResource(R.string.home_page)) stringResource(
                            R.string.home_page
                        )
                        else stringResource(R.string.about_page)
                    )
                },
                    label = { Text(item.title) },
                    selected = currentRoute == item.screen.route,
                    onClick = {
                        navController.navigate(item.screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    })
            }
        }
    }
}