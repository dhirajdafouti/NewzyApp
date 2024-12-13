package hoods.com.newsy.features_presentations.core

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import hoods.com.newsy.features_presentations.core.components.NewysAppDrawerContent
import hoods.com.newsy.features_presentations.core.navigation.UiScreen
import hoods.com.newsy.utils.TestTags
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner


@RunWith(RobolectricTestRunner::class)
class NewsyAppDrawerTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `App drawer display content correctly`() {
        val currentRoute = mutableStateOf(UiScreen.HomeScreen().route)
        composeTestRule.setContent {
            NewysAppDrawerContent(
                currentRoute = currentRoute.value,
                navigateToHome = {},
                navigateToSetting = {
                    currentRoute.value = UiScreen.SettingsScreen().route
                },
                navigateToFavouriteScreen = {},
                closeDrawer = {}
            )
        }

        composeTestRule.onNodeWithTag(TestTags.NewsyLogoIcon).assertIsDisplayed()

        composeTestRule.onNodeWithText("Home")
            .assertIsDisplayed()
            .assertIsSelected()

        composeTestRule.onNodeWithText("Favourite")
            .assertIsDisplayed()
            .assertIsNotSelected()

        composeTestRule.onNodeWithText("Settings")
            .assertIsDisplayed()
            .assertIsNotSelected()
            .performClick()
            .assertIsSelected()

        composeTestRule.onRoot()
            .printToLog("drawer")

    }

}