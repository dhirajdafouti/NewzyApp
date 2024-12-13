package hoods.com.newsy.features_components.headline.data.mapper

import hoods.com.newsy.features_components.core.data.remote.models.Article
import hoods.com.newsy.features_components.core.domain.mapper.Mapper
import hoods.com.newsy.features_components.headline.data.local.model.HeadlineDto

class ArticleHeadlineDtoMapper(
    private val page: Int = 0,
    private val category: String = "",
) : Mapper<Article, HeadlineDto> {
    override fun toModel(value: Article): HeadlineDto {
        return value.run {
            HeadlineDto(
                author = formatEmptyValue(author, "author"),
                content = formatEmptyValue(content, "content"),
                description = formatEmptyValue(description, "description"),
                publishedAt = publishedAt,
                source = source.name,
                title = title,
                url = url,
                urlToImage = urlToImage,
                page = page,
                category = category
            )
        }
    }

    override fun fromModel(value: HeadlineDto): Article {
        return value.run {
            Article(
                author, content, description, publishedAt
            )
        }
    }

    private fun formatEmptyValue(value: String?, default: String = ""): String {
        return value ?: "Unknown $default"
    }
}