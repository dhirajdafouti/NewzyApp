package hoods.com.newsy.features_components.search.data.remote

import hoods.com.newsy.features_components.search.data.remote.models.SearchArticleRemoteDto
import hoods.com.newsy.utils.K
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {
    companion object {
        private const val SEARCH_END_POINT = "/v2/everything"
    }

    @GET(SEARCH_END_POINT)
    suspend fun fetchSearchArticle(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("apiKey") key: String = K.API_KEY,
    ): SearchArticleRemoteDto

}