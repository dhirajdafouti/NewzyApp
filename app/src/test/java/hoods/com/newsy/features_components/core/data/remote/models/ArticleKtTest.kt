package hoods.com.newsy.features_components.core.data.remote.models

import com.google.common.truth.Truth.assertThat
import hoods.com.newsy.utils.Utils
import org.junit.Assert.assertThrows

import org.junit.Test

class ArticleKtTest {
    @Test
    fun `toHeadlineArticle should map to headline correctly`() {
        //GIVEN
        val article = Utils.testArticles[0]
        //WHEN
        val headlineDto = article.toHeadlineArticle(0, "sports")
        //THEN
        assertThat(headlineDto.author).isEqualTo(article.author)
        assertThat(headlineDto.description).isEqualTo(article.description)
        assertThat(headlineDto.content).isEqualTo(article.content)
        assertThat(headlineDto.source).isEqualTo(article.source.name)
        assertThat(headlineDto.title).isEqualTo(article.title)
        assertThat(headlineDto.category).isEqualTo("sports")
        assertThat(headlineDto.favourite).isFalse()
        assertThat(headlineDto.publishedAt).isEqualTo(article.publishedAt)
        assertThat(headlineDto.url).isEqualTo(article.url)
        assertThat(headlineDto.urlToImage).isEqualTo(article.urlToImage)
        assertThat(headlineDto.id).isEqualTo(0)
        assertThat(headlineDto.page).isEqualTo(0)
    }

    @Test
    fun `toHeadlineArticle should map null or empty value with formated output`() {
        //GIVEN
        val articles = Article()
        //WHEN
        val headlineDto = articles.toHeadlineArticle(0, "sports")
        //THEN
        assertThat(headlineDto.author).isEqualTo("Unknown author")
        assertThat(headlineDto.description).isEqualTo("Unknown description")
        assertThat(headlineDto.content).isEqualTo("Unknown content")
        assertThat(headlineDto.source).isEqualTo("Unknown source")
        assertThat(headlineDto.title).isEqualTo("Unknown title")
        assertThat(headlineDto.publishedAt).isEqualTo("Unknown date")
        assertThat(headlineDto.url).isEmpty()
        assertThat(headlineDto.urlToImage).isNull()
    }

    @Test
    fun `toHeadlineArticle should throw IllegalArgument Exc when category is empty`() {
        //GIVEN
        val article = Article()
        //WHEN
        assertThrows(IllegalArgumentException::class.java) {
            article.toHeadlineArticle(0, "")
        }

    }

    @Test
    fun `toHeadlineArticle should throw IndexOutOfBoundException when page is -ve`() {
        //GIVEN
        val article = Article()
        //WHEN
        assertThrows(IndexOutOfBoundsException::class.java) {
            article.toHeadlineArticle(-1, "sports")
        }

    }

    //solution to assignment Test cases
    @Test
    fun `toDiscoverArticle should map to discover correctly`() {
        //GIVEN
        val article = Utils.testArticles[0]
        //WHEN
        val discoverArticle = article.toDiscoverArticle(0, "sports")
        //THEN
        assertThat(discoverArticle.author).isEqualTo(article.author)
        assertThat(discoverArticle.description).isEqualTo(article.description)
        assertThat(discoverArticle.content).isEqualTo(article.content)
        assertThat(discoverArticle.source).isEqualTo(article.source.name)
        assertThat(discoverArticle.title).isEqualTo(article.title)
        assertThat(discoverArticle.category).isEqualTo("sports")
        assertThat(discoverArticle.favourite).isFalse()
        assertThat(discoverArticle.publishedAt).isEqualTo(article.publishedAt)
        assertThat(discoverArticle.url).isEqualTo(article.url)
        assertThat(discoverArticle.urlToImage).isEqualTo(article.urlToImage)
        assertThat(discoverArticle.id).isEqualTo(0)
        assertThat(discoverArticle.page).isEqualTo(0)
    }

    @Test
    fun `toDiscoverArticle should map null or empty value with formated output`() {
        //GIVEN
        val article = Article()
        //WHEN
        val discoverArticle = article.toDiscoverArticle(0, "sports")
        //THEN
        assertThat(discoverArticle.author).isEqualTo("Unknown author")
        assertThat(discoverArticle.description).isEqualTo("Unknown description")
        assertThat(discoverArticle.content).isEqualTo("Unknown content")
        assertThat(discoverArticle.source).isEqualTo("Unknown source")
        assertThat(discoverArticle.title).isEqualTo("Unknown title")
        assertThat(discoverArticle.publishedAt).isEqualTo("Unknown date")
        assertThat(discoverArticle.url).isEmpty()
        assertThat(discoverArticle.urlToImage).isNull()
    }

    @Test
    fun `toDiscoverArticle should throw IllegalArgumentException when category is empty`() {
        //GIVEN
        val article = Article()
        //WHEN
        assertThrows(IllegalArgumentException::class.java) {
            article.toDiscoverArticle(0, "")
        }
        //THEN

    }

    @Test
    fun `toDiscoverArticle should throw IndexOutOfBoundException when page is -ve`() {
        //GIVEN
        val article = Article()
        //WHEN
        assertThrows(IndexOutOfBoundsException::class.java) {
            article.toDiscoverArticle(-1, "sports")
        }
        //THEN

    }
}