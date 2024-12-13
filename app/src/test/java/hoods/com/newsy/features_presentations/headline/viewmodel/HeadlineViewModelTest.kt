package hoods.com.newsy.features_presentations.headline.viewmodel

import androidx.paging.testing.asSnapshot
import com.google.common.truth.Truth.assertThat
import hoods.com.newsy.MainDispatcherRule
import hoods.com.newsy.features_components.FakeHeadlineRepo
import hoods.com.newsy.features_components.core.domain.models.Setting
import hoods.com.newsy.features_components.core.domain.use_cases.GetSettingUseCase
import hoods.com.newsy.features_components.core.domain.use_cases.InsertSettingUseCase
import hoods.com.newsy.features_components.core.domain.use_cases.SettingUseCases
import hoods.com.newsy.features_components.core.domain.use_cases.UpdateSettingUseCase
import hoods.com.newsy.features_components.headline.domain.use_cases.FetchHeadlineArticleUseCase
import hoods.com.newsy.features_components.headline.domain.use_cases.HeadlineUseCases
import hoods.com.newsy.features_components.headline.domain.use_cases.UpdateHeadlineFavouriteUseCase
import hoods.com.newsy.features_presentations.FakeSettingRepository
import hoods.com.newsy.utils.Utils
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class HeadlineViewModelTest {

    private val fakeHeadlineRepo = FakeHeadlineRepo()
    private val fakeSettingRepository = FakeSettingRepository()
    lateinit var headlineUseCases: HeadlineUseCases
    lateinit var settingUseCases: SettingUseCases
    lateinit var headlineViewModel: HeadlineViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()



    @Before
    fun setUp() {
        headlineUseCases = HeadlineUseCases(
            fetchHeadlineArticleUseCase = FetchHeadlineArticleUseCase(repository = fakeHeadlineRepo),
            updateHeadlineFavouriteUseCase = UpdateHeadlineFavouriteUseCase(fakeHeadlineRepo)
        )
        settingUseCases = SettingUseCases(
            getSettingUseCase = GetSettingUseCase(fakeSettingRepository),
            insertSettingUseCase = InsertSettingUseCase(fakeSettingRepository),
            updateSettingUseCase = UpdateSettingUseCase(fakeSettingRepository)
        )
        headlineViewModel = HeadlineViewModel(headlineUseCases, settingUseCases)
    }

    @Test
    fun `initHeadlineArticleData should update headlineState with fetched article`() = runTest {
        val state = headlineViewModel.headlineState
        val actualArticles = state.headlineArticles.asSnapshot()

        assertThat(actualArticles).isNotEmpty()
        assertThat(actualArticles.first().title).isEqualTo("Title of Article One")

    }

    @Test
    fun `InitSettings should update headline state`() = runTest {
        //GIVEN
        val expectedSetting = Setting(1, 1)
        settingUseCases.insertSettingUseCase(expectedSetting)
        //WHEN
        headlineViewModel = HeadlineViewModel(headlineUseCases, settingUseCases)
        //THEN
        val state = headlineViewModel.headlineState
        assertThat(state.setting).isEqualTo(expectedSetting)

    }

    @Test
    fun `onFavouriteChange should toggle favourite status and call update useCase`() = runTest {
        //GIVEN
         val article = Utils.newsyArticles[0]
        //WHEN
        headlineViewModel.onFavouriteChange(article)
        //THEN
        val actualArticle = headlineViewModel.headlineState.headlineArticles.asSnapshot().first()
        assertThat(actualArticle.favourite).isTrue()
    }

}