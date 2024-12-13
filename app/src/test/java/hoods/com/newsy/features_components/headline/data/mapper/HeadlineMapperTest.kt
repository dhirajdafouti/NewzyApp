package hoods.com.newsy.features_components.headline.data.mapper

import com.google.common.truth.Truth.assertThat
import hoods.com.newsy.features_components.core.domain.mapper.Mapper
import hoods.com.newsy.features_components.core.domain.models.NewsyArticle
import hoods.com.newsy.features_components.headline.data.local.model.HeadlineDto

import org.junit.Before
import org.junit.Test

class HeadlineMapperTest {
    lateinit var headlineMapper: Mapper<HeadlineDto, NewsyArticle>

    @Before
    fun setUp() {
        headlineMapper = HeadlineMapper()
    }

    @Test
    fun `fromModel should map to HeadlineDto correctly`() {
        //GIVEN
        val newsyArticle = NewsyArticle(
            id = 0,
            author = "author",
            content = "content",
            description = "description",
            publishedAt = "publishedAt",
            source = "source",
            title = "title",
            url = "url",
            urlToImage = "urlToImage",
            favourite = false,
            category = "sports",
            page = 0
        )
        //WHEN
        val headlineDto = headlineMapper.fromModel(newsyArticle)
        //THEN
        assertThat(headlineDto.author).isEqualTo(newsyArticle.author)
        assertThat(headlineDto.description).isEqualTo(newsyArticle.description)
        assertThat(headlineDto.content).isEqualTo(newsyArticle.content)
        assertThat(headlineDto.source).isEqualTo(newsyArticle.source)
        assertThat(headlineDto.title).isEqualTo(newsyArticle.title)
        assertThat(headlineDto.category).isEqualTo("sports")
        assertThat(headlineDto.favourite).isFalse()
        assertThat(headlineDto.publishedAt).isEqualTo(newsyArticle.publishedAt)
        assertThat(headlineDto.url).isEqualTo(newsyArticle.url)
        assertThat(headlineDto.urlToImage).isEqualTo(newsyArticle.urlToImage)
        assertThat(headlineDto.id).isEqualTo(0)
        assertThat(headlineDto.page).isEqualTo(0)
    }

    //Solution for Test case

    @Test
    fun `toModel should map to NewsyArticle correctly`() {
        //GIVEN
        val testHeadlineDto = HeadlineDto(
            id = 0,
            author = "author",
            content = "content",
            description = "description",
            publishedAt = "publishedAt",
            source = "source",
            title = "title",
            url = "url",
            urlToImage = "urlToImage",
            favourite = false,
            category = "sports",
            page = 0
        )
        //WHEN
        val newsyArticle = headlineMapper.toModel(testHeadlineDto)
        //THEN
        assertThat(newsyArticle.author).isEqualTo(testHeadlineDto.author)
        assertThat(newsyArticle.description).isEqualTo(testHeadlineDto.description)
        assertThat(newsyArticle.content).isEqualTo(testHeadlineDto.content)
        assertThat(newsyArticle.source).isEqualTo(testHeadlineDto.source)
        assertThat(newsyArticle.title).isEqualTo(testHeadlineDto.title)
        assertThat(newsyArticle.category).isEqualTo("sports")
        assertThat(newsyArticle.favourite).isFalse()
        assertThat(newsyArticle.publishedAt).isEqualTo(testHeadlineDto.publishedAt)
        assertThat(newsyArticle.url).isEqualTo(testHeadlineDto.url)
        assertThat(newsyArticle.urlToImage).isEqualTo(testHeadlineDto.urlToImage)
        assertThat(newsyArticle.id).isEqualTo(0)
        assertThat(newsyArticle.page).isEqualTo(0)
    }


}