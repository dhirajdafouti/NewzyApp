package hoods.com.newsy.features_presentations.detail.viewmodel

import hoods.com.newsy.features_components.detail.domain.models.DetailArticle
import hoods.com.newsy.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class DetailState(
    val selectedDetailArticle: Flow<Resource<DetailArticle>> = emptyFlow(),
    val error: Boolean = false,
)
