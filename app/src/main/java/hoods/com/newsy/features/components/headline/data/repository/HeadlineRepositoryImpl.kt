package hoods.com.newsy.features.components.headline.data.repository

import hoods.com.newsy.core.data.helper.NetworkModule
import hoods.com.newsy.core.domain.util.NetworkError
import hoods.com.newsy.core.domain.util.Result
import hoods.com.newsy.features.components.headline.data.api.HeadlineApi
import hoods.com.newsy.features.components.headline.data.mapper.toCoin
import hoods.com.newsy.features.components.headline.domain.entity.HeadlineEntity
import hoods.com.newsy.features.components.headline.domain.repository.HeadlineRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HeadlineRepositoryImpl @Inject constructor(
    private val networkModule: NetworkModule,
    private val headlineApi: HeadlineApi
) : HeadlineRepository {

    override suspend fun getHeadlines(): Result<HeadlineEntity, NetworkError> {
        val response = networkModule.safeApiCall {
            headlineApi.getHeadlines()
        }
        return when (response) {
            is Result.Success -> {
                val headlineEntity = response.data.toCoin()
                Result.Success(headlineEntity)
            }

            is Result.Error -> {
                Result.Error(response.error)
            }
        }
    }

}
