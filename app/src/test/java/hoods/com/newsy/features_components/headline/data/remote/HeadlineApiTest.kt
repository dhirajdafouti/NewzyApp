package hoods.com.newsy.features_components.headline.data.remote

import com.google.common.truth.Truth.assertThat
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import hoods.com.newsy.MainDispatcherRule
import hoods.com.newsy.utils.MockResponseFileReader
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Retrofit
import java.io.IOException

class HeadlineApiTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var api: HeadlineApi
    private val json = Json {
        coerceInputValues = true
        ignoreUnknownKeys = true
    }
    private val contentType = "application/json".toMediaType()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val successJsonFileName = "get_article_success.json"


    @Before
    fun setUp() {
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
        mockWebServer.shutdown()
    }


    @Test
    fun `getHeadlines should hot correct endpoint`() = runTest {
        //GIVEN
        mockWebServer.enqueue(MockResponse().setBody("{}").setResponseCode(200))
        val expectedEndPoint =
            "/v2/top-headlines?apiKey=API_KEY&category=sports&country=us&language=en&page=0&pageSize=20"
        //WHEN
        api.getHeadlines(
            key = "API_KEY",
            category = "sports",
            country = "us",
            language = "en",
            pageSize = 20,
            page = 0
        )
        //THEN
        val request = mockWebServer.takeRequest()
        assertThat(request.path).isEqualTo(expectedEndPoint)
    }

    @Test
    fun `getHeadline should return correct data`() = runTest {
        //GIVEN
        val successJson = MockResponseFileReader(successJsonFileName).content
        mockWebServer.enqueue(MockResponse().setBody(successJson).setResponseCode(200))
        //WHEN
        val actualResponse = api.getHeadlines(
            key = "API_KEY",
            category = "sports",
            country = "us",
            language = "en",
            pageSize = 20,
            page = 0
        )
        //THEN
        assertThat(actualResponse).isNotNull()
        assertThat(actualResponse.totalResults).isEqualTo(61)
        assertThat(actualResponse.status).isEqualTo("ok")
        assertThat(actualResponse.articles[0].author).isEqualTo("CNN")

    }

    @Test(expected = HttpException::class)
    fun `getHeadline article handles 404 error`() = runTest {
        //GIVEN
        mockWebServer.enqueue(MockResponse().setResponseCode(404))
        //WHEN
        api.getHeadlines(
            key = "API_KEY",
            category = "sports",
            country = "us",
            language = "en",
            pageSize = 20,
            page = 0
        )


    }

    @Test(expected = IOException::class)
    fun `getHeadline article handles network failure`() = runTest {
        //GIVEN
        mockWebServer.shutdown()
        //WHEN
        api.getHeadlines(
            key = "API_KEY",
            category = "sports",
            country = "us",
            language = "en",
            pageSize = 20,
            page = 0
        )


    }


}