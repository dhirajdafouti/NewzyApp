package hoods.com.newsy.features_components.headline.data.mapper

import hoods.com.newsy.features_components.core.domain.mapper.Mapper
import hoods.com.newsy.features_components.core.domain.models.NewsyArticle
import hoods.com.newsy.features_components.headline.data.local.model.HeadlineDto

class HeadlineMapper:Mapper<HeadlineDto,NewsyArticle> {
    override fun toModel(value: HeadlineDto): NewsyArticle {
        return value.run {
            NewsyArticle(
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

    override fun fromModel(value: NewsyArticle): HeadlineDto {
        return value.run {
            HeadlineDto(
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