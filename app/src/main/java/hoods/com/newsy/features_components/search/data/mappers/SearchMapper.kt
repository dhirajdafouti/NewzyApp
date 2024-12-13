package hoods.com.newsy.features_components.search.data.mappers

import hoods.com.newsy.features_components.core.domain.mapper.Mapper
import hoods.com.newsy.features_components.search.data.local.models.SearchDto
import hoods.com.newsy.features_components.search.domain.models.SearchArticle

class SearchMapper : Mapper<SearchDto, SearchArticle> {
    override fun toModel(value: SearchDto): SearchArticle {
        return value.run {
            SearchArticle(
                id = id,
                author = author,
                content = content,
                description = description,
                publishedAt = publishedAt,
                source = source,
                title = title,
                url = url,
                urlToImage = urlToImage,
                favourite = favourite,
                category = category,
                page = page
            )
        }
    }

    override fun fromModel(value: SearchArticle): SearchDto {
        return value.run {
            SearchDto(
                id = id,
                author = author,
                content = content,
                description = description,
                publishedAt = publishedAt,
                source = source,
                title = title,
                url = url,
                urlToImage = urlToImage,
                favourite = favourite,
                category = category,
                page = page
            )
        }
    }
}