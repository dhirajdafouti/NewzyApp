package hoods.com.newsy.features_presentations.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination

class NewsyNavigationActions(
    navController: NavController,
) {
    val navigateToHome: () -> Unit = {
        navController.navigateToSingleTop(
            UiScreen.HomeScreen().route
        )
    }
    val navigateToDetail: (
        id: Int,
        screenType: String,
    ) -> Unit = { id, screenType ->
        navController.navigateToSingleTop(
            route = "${UiScreen.DetailScreen().route}/$id&$screenType"
        )
    }
    val navigateToHeadlineScreen: () -> Unit = {
        navController.navigateToSingleTop(
            UiScreen.HeadlineScreen().route
        )
    }

    val navigateToSettingsScreen: () -> Unit = {
        navController.navigateToSingleTop(
            UiScreen.SettingsScreen().route
        )
    }

    val navigateToFavouriteScreen: () -> Unit = {
        navController.navigateToSingleTop(
            UiScreen.FavouriteScreen().route
        )
    }
    val navigateToSearch: () -> Unit = {
        navController.navigateToSingleTop(
            UiScreen.SearchScreen().route
        )
    }
}

fun NavController.navigateToSingleTop(route: String) {
    navigate(route) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}