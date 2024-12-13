package hoods.com.newsy.features_components.headline.data.local.dao

import androidx.room.Room
import com.google.common.truth.Truth.assertThat
import hoods.com.newsy.MainDispatcherRule
import hoods.com.newsy.features_components.core.data.local.NewsyArticleDatabase
import hoods.com.newsy.utils.Utils
import hoods.com.newsy.utils.getTestData
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class HeadlineDaoTest {
    private lateinit var headlineDao: HeadlineDao
    private lateinit var db: NewsyArticleDatabase

    @get:Rule
    val mainCoroutineDispatcher = MainDispatcherRule()

    @Before
    fun setUp() {
        val app = RuntimeEnvironment.getApplication()
        db = Room.inMemoryDatabaseBuilder(
            context = app.applicationContext,
            klass = NewsyArticleDatabase::class.java
        ).allowMainThreadQueries().build()
        headlineDao = db.headlineDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `getAllArticles return all articles from db correctly`() = runTest {
        //GIVEN
        val expected = Utils.headlineDto[0]
        headlineDao.insertHeadlineArticle(listOf(expected))
        //WHEN
        val actual = headlineDao.getAllHeadlineArticles().getTestData()
        //THEN
        assertThat(actual[0]).isEqualTo(expected.copy(id = 1))
    }

    //Solution for Assignment test case
    @Test
    fun `getHeadlineArticle returns article by id`() = runTest {
        //GIVEN
        val expected = Utils.headlineDto
        headlineDao.insertHeadlineArticle(expected)
        //WHEN
        val actual = headlineDao.getHeadlineArticle(id = 1).first()
        //THEN
        assertThat(actual.author).isEqualTo(expected[0].author)
        assertThat(actual.content).isEqualTo(expected[0].content)
        assertThat(actual.description).isEqualTo(expected[0].description)
        assertThat(actual.publishedAt).isEqualTo(expected[0].publishedAt)
        assertThat(actual.id).isEqualTo(1)
    }

    @Test
    fun `removeFavouriteArticle deletes favourite article from db`() = runTest {
        //GIVEN
        val headlineArticle = Utils.headlineDto
        val headlineArticleFav = Utils.headlineDto[0].copy(favourite = true)
        headlineDao.insertHeadlineArticle(listOf(headlineArticleFav))
        headlineDao.insertHeadlineArticle(headlineArticle)
        //WHEN
        headlineDao.removeFavouriteArticle(1)
        headlineDao.removeFavouriteArticle(2)
        //THEN
        val actual = headlineDao.getHeadlineArticle(1).first()
        val actual2 = headlineDao.getHeadlineArticle(2).first()
        assertThat(actual).isNull()
        assertThat(actual2).isNotNull()
    }

    @Test
    fun `updateFavouriteArticle update article favourite true or false`() = runTest {
        //GIVEN
        val headlineArticleFav = Utils.headlineDto[0].copy(favourite = true)
        val headlineArticleNotFav = Utils.headlineDto[1]
        headlineDao.insertHeadlineArticle(listOf(headlineArticleFav, headlineArticleNotFav))
        //WHEN
        headlineDao.updateFavouriteArticle(isFavourite = false, id = 1)
        headlineDao.updateFavouriteArticle(isFavourite = true, id = 2)
        val actual = headlineDao.getAllHeadlineArticles().getTestData()
        //THEN
        assertThat(actual[0].favourite).isFalse()
        assertThat(actual[1].favourite).isTrue()
    }


}