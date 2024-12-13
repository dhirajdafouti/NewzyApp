package hoods.com.newsy.features_presentations.core.navigation

import hoods.com.newsy.utils.K

sealed class UiScreen {
    data class HomeScreen(val route: String = "home") : UiScreen()
    data class HeadlineScreen(val route: String = "headline") : UiScreen()
    data class DiscoverScreen(val route: String = "discover") : UiScreen()
    data class DetailScreen(
        val route: String = "home",
        val id: String = K.articleId,
        val screen: String = K.screenType,
        val routeWithArg: String = "$route/{${K.articleId}}&{${K.screenType}}",
    ) : UiScreen()

    data class SearchScreen(val route: String = "search") : UiScreen()
    data class FavouriteScreen(val route: String = "favourite") : UiScreen()
    data class SettingsScreen(val route: String = "settings") : UiScreen()

}
