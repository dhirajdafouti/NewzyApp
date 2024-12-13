package hoods.com.newsy.features_presentations.core

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import hoods.com.newsy.features_presentations.core.components.NewysAppDrawerContent
import hoods.com.newsy.features_presentations.core.navigation.NewsyNavigationActions
import hoods.com.newsy.features_presentations.core.navigation.NewsyNavigationGraph
import hoods.com.newsy.features_presentations.core.navigation.UiScreen
import hoods.com.newsy.utils.TestTags
import kotlinx.coroutines.launch

@Composable
fun NewsyApp() {
    val navController = rememberNavController()
    val navActions = remember {
        NewsyNavigationActions(navController)
    }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute =
        navBackStackEntry?.destination?.route ?: UiScreen.HomeScreen().route
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerContent = {
            NewysAppDrawerContent(
                currentRoute = currentRoute,
                navigateToHome = navActions.navigateToHome,
                navigateToSetting = navActions.navigateToSettingsScreen,
                navigateToFavouriteScreen = navActions.navigateToFavouriteScreen,
                closeDrawer = {
                    scope.launch {
                        drawerState.close()
                    }
                },
                modifier = Modifier.testTag(TestTags.DrawerContainer)
            )
        },
        drawerState = drawerState
    ) {
        NewsyNavigationGraph(
            navController = navController,
            navActions = navActions,
            openDrawer = {
                scope.launch {
                    drawerState.open()
                }
            }
        )
    }


}