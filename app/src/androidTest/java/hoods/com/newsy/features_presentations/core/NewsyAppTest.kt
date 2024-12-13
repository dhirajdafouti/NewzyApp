package hoods.com.newsy.features_presentations.core

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import hoods.com.newsy.utils.MockResponseFileReader
import hoods.com.newsy.utils.TestTags
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
class NewsyAppTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var mockWebServer: MockWebServer
    private val successJsonFileName = "get_article_success.json"

    @Before
    fun setUp() {
        hiltRule.inject()
        mockWebServer = MockWebServer()
        mockWebServer.start(8080)
        val jsonResponse = MockResponseFileReader(successJsonFileName).content
        mockWebServer.enqueue(MockResponse().setBody(jsonResponse).setResponseCode(200))
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }


    @Test
    fun app_launches() {
        composeTestRule.onNodeWithContentDescription("Newsy").assertIsDisplayed()
        composeTestRule.onNodeWithText("Hot News", useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("Discover News", useUnmergedTree = true).assertIsDisplayed()
    }


    @Test
    fun app_can_navigate_with_drawers() {
        //App launches in Home Screen
        composeTestRule.onNodeWithContentDescription("Newsy").assertIsDisplayed()
        composeTestRule.onNodeWithText("Hot News", useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("Discover News", useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TestTags.DrawerBtn,useUnmergedTree = true).performClick()

        //DrawerScreen
        composeTestRule.onNodeWithTag(TestTags.DrawerContainer).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Home").performClick()
        composeTestRule.onNodeWithTag(TestTags.DrawerContainer).assertIsNotDisplayed()

        //Home Screen
        composeTestRule.onNodeWithContentDescription("Newsy").assertIsDisplayed()
        composeTestRule.onNodeWithTag(TestTags.DrawerBtn,useUnmergedTree = true).performClick()
        //Favourite Screen
        composeTestRule.onNodeWithContentDescription("Favourite").performClick()
        composeTestRule.onNodeWithTag(TestTags.DrawerContainer).assertIsNotDisplayed()
        composeTestRule.onNodeWithText("Favourite Screen").assertIsDisplayed()
        //Setting Screen
        composeTestRule.onNodeWithTag(TestTags.DrawerBtn,useUnmergedTree = true).performClick()
        composeTestRule.onNodeWithContentDescription("Settings").performClick()
        composeTestRule.onNodeWithTag(TestTags.DrawerContainer).assertIsNotDisplayed()
        composeTestRule.onNodeWithText("Settings Screen").assertIsDisplayed()

    }


}