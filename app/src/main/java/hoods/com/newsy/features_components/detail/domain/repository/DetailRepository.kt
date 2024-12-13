package hoods.com.newsy.features_components.detail.domain.repository

import hoods.com.newsy.features_components.detail.domain.models.DetailArticle
import hoods.com.newsy.utils.Resource
import kotlinx.coroutines.flow.Flow

interface DetailRepository {
    suspend fun getHeadlineArticleById(id: Int): Flow<Resource<DetailArticle>>
    suspend fun getDiscoverArticleById(id: Int): Flow<Resource<DetailArticle>>
    suspend fun getSearchArticleById(id: Int): Flow<Resource<DetailArticle>>
}