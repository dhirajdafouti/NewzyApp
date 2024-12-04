package hoods.com.newsy.features.components.headline.data.api

import hoods.com.newsy.features.components.headline.data.model.NewsyRemoteDto
import retrofit2.Response
import retrofit2.http.GET

interface HeadlineApi {
    @GET( "/v2/top-headlines")
    suspend fun getHeadlines(
    ): Response<NewsyRemoteDto>

}
