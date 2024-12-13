package hoods.com.newsy.features_components.headline.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.Room
import com.google.common.truth.Truth.assertThat
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import hoods.com.newsy.MainDispatcherRule
import hoods.com.newsy.features_components.core.data.local.NewsyArticleDatabase
import hoods.com.newsy.features_components.headline.data.local.model.HeadlineDto
import hoods.com.newsy.features_components.headline.data.local.model.HeadlineRemoteKey
import hoods.com.newsy.features_components.headline.data.remote.HeadlineApi
import hoods.com.newsy.utils.MockResponseFileReader
import hoods.com.newsy.utils.getTestData
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class HeadlineRemoteMediatorTest {
    private lateinit var db: NewsyArticleDatabase

    @get:Rule
    val mainCoroutineRule = MainDispatcherRule()

    private lateinit var api: HeadlineApi
    private lateinit var mockWebServer: MockWebServer
    private val json = Json {
        coerceInputValues = true
        ignoreUnknownKeys = true
    }
    private val contentType = "application/json".toMediaType()
    private val successJsonFileName = "get_article_success.json"
    private val invalidAPiKeyJsonFileName = "get_article_invalid_api_key.json"

    @Before
    fun setUp() {
        val app = RuntimeEnvironment.getApplication()
        db = Room.inMemoryDatabaseBuilder(
            context = app.applicationContext,
            klass = NewsyArticleDatabase::class.java
        ).allowMainThreadQueries().build()

        mockWebServer = MockWebServer()
        mockWebServer.start()
        api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(HeadlineApi::class.java)
    }

    @After
    fun tearDown() {
        db.close()
        mockWebServer.shutdown()
    }


    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun `initialize cache not expired returns SkipInitialRefresh`() = runTest {
        //GIVEN
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(20, TimeUnit.MINUTES)
        val validCacheTime = System.currentTimeMillis() + cacheTimeout
        db.headlineRemoteDao().insertAll(
            listOf(
                HeadlineRemoteKey(
                    articleId = "test_id",
                    prevKey = null,
                    nextKey = null,
                    currentPage = 0,
                    createdAt = validCacheTime
                )
            )
        )

        val remoteMediator = HeadlineRemoteMediator(api, db)
        //WHEN
        val result = remoteMediator.initialize()
        //THEN
        assertThat(result).isEqualTo(RemoteMediator.InitializeAction.SKIP_INITIAL_REFRESH)
    }


    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun `initialize cache  expired returns LaunchInitialRefresh`() = runTest {
        //GIVEN
        val expiredCacheTime =
            System.currentTimeMillis() - TimeUnit.MILLISECONDS.convert(30, TimeUnit.MINUTES)
        db.headlineRemoteDao().insertAll(
            listOf(
                HeadlineRemoteKey(
                    articleId = "test_id",
                    prevKey = null,
                    nextKey = null,
                    currentPage = 0,
                    createdAt = expiredCacheTime
                )
            )
        )

        val remoteMediator = HeadlineRemoteMediator(api, db)
        //WHEN
        val result = remoteMediator.initialize()
        //THEN
        assertThat(result).isEqualTo(RemoteMediator.InitializeAction.LAUNCH_INITIAL_REFRESH)
    }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun `refresh load returns success results when more data is present`() = runTest {
        //GIVEN
        val responseJson = MockResponseFileReader(successJsonFileName).content
        mockWebServer.enqueue(MockResponse().setBody(responseJson).setResponseCode(200))
        val remoteMediator = HeadlineRemoteMediator(api, db)
        val pagingState = PagingState<Int, HeadlineDto>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )
        //WHEN
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        //THEN
        val insertedData = db.headlineDao().getAllHeadlineArticles().getTestData()
        assertThat(result is RemoteMediator.MediatorResult.Success).isTrue()
        assertThat((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached).isFalse()
        assertThat(insertedData).isNotEmpty()
    }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun `refresh load returns Error when error occurs`() = runTest {
        //GIVEN
        val responseJson = MockResponseFileReader(invalidAPiKeyJsonFileName).content
        mockWebServer.enqueue(MockResponse().setBody(responseJson).setResponseCode(401))
        val remoteMediator = HeadlineRemoteMediator(api, db)
        val pagingState = PagingState<Int, HeadlineDto>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )
        //WHEN
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        //THEN

        assertThat(result is RemoteMediator.MediatorResult.Error).isTrue()
    }


}