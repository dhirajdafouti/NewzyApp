package hoods.com.newsy.features_presentations.headline

import androidx.activity.compose.setContent
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.printToLog
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import hoods.com.newsy.di.HeadlineModule
import hoods.com.newsy.di.NewsyLocalModule
import hoods.com.newsy.features_components.core.domain.models.NewsyArticle
import hoods.com.newsy.features_presentations.core.MainActivity
import hoods.com.newsy.features_presentations.core.navigation.UiScreen
import hoods.com.newsy.features_presentations.core.ui.theme.NewsyTheme
import hoods.com.newsy.utils.MockResponseFileReader
import hoods.com.newsy.utils.TestTags
import hoods.com.newsy.utils.Utils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(HeadlineModule::class, NewsyLocalModule::class)
class HeadlineScreenKtTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var mockWebServer: MockWebServer
    private val successJson = "get_article_success.json"

    @Before
    fun setUp() {
        hiltRule.inject()
        mockWebServer = MockWebServer()
        mockWebServer.start(8080)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun headlineScreen_Displays_Articles_Correctly() = runTest {
        val jsonResponse = MockResponseFileReader(successJson).content
        mockWebServer.enqueue(MockResponse().setBody(jsonResponse).setResponseCode(200))
        setActivityContent()
        composeTestRule.awaitIdle()
        composeTestRule.onRoot().printToLog("headlineScreen")
        composeTestRule.onNodeWithText("Hot news").assertIsDisplayed()
        composeTestRule.onNodeWithText("ESPN", useUnmergedTree = true).assertIsDisplayed()
    }

    @Test
    fun headlineScreen_showsArticlesCorrectly_when_scrolled() = runTest {
        val articles = flowOf(
            PagingData.from(
                data = Utils.newsyArticles,
                sourceLoadStates = LoadStates(
                    refresh = LoadState.NotLoading(endOfPaginationReached = false),
                    append = LoadState.NotLoading(endOfPaginationReached = false),
                    prepend = LoadState.NotLoading(endOfPaginationReached = false),
                )
            )
        )

        setHeadlineContent(articles)
        composeTestRule.onNodeWithTag(TestTags.HeadlineProgressIndicator).assertDoesNotExist()
        composeTestRule.onNodeWithTag(TestTags.HeadlineLazyColumnTestTag)
            .performScrollToIndex(Utils.newsyArticles.lastIndex)
        composeTestRule.awaitIdle()
        composeTestRule.onNodeWithText(Utils.newsyArticles.last().title).assertIsDisplayed()
    }

    @Test
    fun headlineScreen_appendsNewData() = runTest {
        val initialArticles = Utils.newsyArticles.take(5)
        val appendedArticles = Utils.newsyArticles.takeLast(5)

        val articleFlow = MutableStateFlow(
            PagingData.from(data = initialArticles)
        )
        setHeadlineContent(articleFlow)
        composeTestRule.onNodeWithTag(TestTags.HeadlineLazyColumnTestTag)
            .performScrollToIndex(initialArticles.lastIndex)
        composeTestRule.onAllNodesWithTag(TestTags.NewsyArticle)
            .assertCountEquals(initialArticles.size)
        composeTestRule.awaitIdle()

        val finalArticle = initialArticles + appendedArticles
        articleFlow.value = PagingData.from(
            data = finalArticle,
            sourceLoadStates = LoadStates(
                refresh = LoadState.NotLoading(endOfPaginationReached = false),
                append = LoadState.NotLoading(endOfPaginationReached = false),
                prepend = LoadState.NotLoading(endOfPaginationReached = false),
            )
        )
        composeTestRule.awaitIdle()
        composeTestRule.onNodeWithTag(TestTags.HeadlineLazyColumnTestTag)
            .performScrollToIndex(finalArticle.size)
        composeTestRule.onAllNodesWithTag(TestTags.NewsyArticle)
            .assertCountEquals(finalArticle.size)
        composeTestRule.onNodeWithText(appendedArticles.last().title).assertIsDisplayed()

    }

    @Test
    fun headlineScreen_error_display_snackBar() = runTest {
        val articles = flowOf(
            PagingData.from(
                data = emptyList<NewsyArticle>(),
                sourceLoadStates = LoadStates(
                    refresh = LoadState.Error(Throwable("Network error")),
                    append = LoadState.NotLoading(endOfPaginationReached = true),
                    prepend = LoadState.NotLoading(endOfPaginationReached = true),
                ),
                mediatorLoadStates = LoadStates(
                    refresh = LoadState.Error(Throwable("Network error")),
                    append = LoadState.NotLoading(endOfPaginationReached = true),
                    prepend = LoadState.NotLoading(endOfPaginationReached = true),
                )
            )
        )

        setHeadlineContent(articles)

        composeTestRule.onNodeWithTag(TestTags.HeadlineLazyColumnTestTag, useUnmergedTree = true)
            .assertExists()
            .printToLog(tag = "error")
        composeTestRule.onNode(
            hasText("Network error"),
            useUnmergedTree = true
        ).assertIsDisplayed()
        composeTestRule.awaitIdle()

    }


    private fun setActivityContent() {
        composeTestRule.activity.setContent {
            NewsyTheme {
                NavHost(
                    navController = rememberNavController(),
                    startDestination = UiScreen.HeadlineScreen().route
                ) {
                    composable(route = UiScreen.HeadlineScreen().route) {
                        HeadlineScreen {

                        }
                    }
                }
            }
        }
    }

    private fun setHeadlineContent(articles: Flow<PagingData<NewsyArticle>>) {
        composeTestRule.activity.setContent {
            HeadlineScreen(
                articles = articles.collectAsLazyPagingItems(),
                onItemClick = {},
                onFavouriteChange = {}
            )
        }
    }


}