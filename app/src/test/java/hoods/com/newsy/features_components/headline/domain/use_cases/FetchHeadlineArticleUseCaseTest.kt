package hoods.com.newsy.features_components.headline.domain.use_cases

import androidx.paging.testing.asSnapshot
import com.google.common.truth.Truth.assertThat
import hoods.com.newsy.features_components.FakeHeadlineRepo
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertThrows
import org.junit.Test


class FetchHeadlineArticleUseCaseTest {
    private val repository = FakeHeadlineRepo()

    private val fetchHeadlineArticleUseCase = FetchHeadlineArticleUseCase(repository)

    @Test
    fun `fetchHeadlineArticleUseCase returns paging data correctly`() = runTest {
        val result = fetchHeadlineArticleUseCase.invoke("sports", "us", "en")
        val pagingData = result.asSnapshot()
        assertThat(pagingData).isNotEmpty()
        assertThat(pagingData.first().title).isEqualTo("Title of Article One")

    }

    @Test
    fun `fetchHeadlineArticleUseCase should throw exception whe category is empty`() {
        assertThrows(IllegalArgumentException::class.java) {
            fetchHeadlineArticleUseCase("", "us", "en")
        }
    }

    @Test
    fun `fetchHeadlineArticleUseCase should throw exception whe country is empty`() {
        assertThrows(IllegalArgumentException::class.java) {
            fetchHeadlineArticleUseCase("sports", "", "en")
        }
    }

    @Test
    fun `fetchHeadlineArticleUseCase should throw exception whe language is empty`() {
        assertThrows(IllegalArgumentException::class.java) {
            fetchHeadlineArticleUseCase("sports", "us", "")
        }
    }

}