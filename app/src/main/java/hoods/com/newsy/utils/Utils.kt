package hoods.com.newsy.utils

import hoods.com.newsy.R
import hoods.com.newsy.features_components.core.data.remote.models.Article
import hoods.com.newsy.features_components.core.data.remote.models.Source
import hoods.com.newsy.features_components.core.domain.models.Country
import hoods.com.newsy.features_components.core.domain.models.Language
import hoods.com.newsy.features_components.core.domain.models.NewsyArticle
import hoods.com.newsy.features_components.headline.data.local.model.HeadlineDto
import java.text.SimpleDateFormat
import java.util.Locale

object Utils {
    val countryCodeList = listOf(
        Country("us", "United States", R.drawable.ic_united_states),
        Country("za", "South Africa", R.drawable.ic_south_africa),
        Country("jp", "Japan", R.drawable.ic_japan),
        Country("de", "Germany", R.drawable.ic_germany),
        Country("cn", "China", R.drawable.ic_china),
        Country("ae", "United Arab Emirates", R.drawable.ic_united_arab_emirates),
        Country("in", "India", R.drawable.ic_india),
        Country("br", "Brazil", R.drawable.ic_brazil),
        Country("tr", "Turkey", R.drawable.ic_turkey),
        Country("ua", "Ukraine", R.drawable.ic_ukraine),
        Country("gb", "United Kingdom", R.drawable.ic_uk)
    )
    val languageCodeList = listOf(
        Language("en", "English"),
        Language("ar", "Arabic"),
        Language("de", "German"),
        Language("es", "Spanish"),
        Language("fr", "French"),
        Language("tr", "Turkish"),
    )

    val testArticles = listOf(
        Article(
            source = Source(id = "a", name = "Gizmodo.com"),
            author = "Harri Weber",
            title = "Trump Promises to Make U.S. the ‘Crypto Capital of the Planet and the Bitcoin Superpower’",
            description = "The former president drew cheers at the 2024 Bitcoin Conference after saying he'll \"fire\" Biden-nominated SEC Chair Gary Gensler.",
            url = "https://gizmodo.com/trump-promises-to-make-u-s-the-crypto-capital-of-the-planet-and-the-bitcoin-superpower-2000480037",
            urlToImage = "https://gizmodo.com/app/uploads/2024/07/Screenshot-2024-07-27-at-1.02.37 PM.jpg",
            publishedAt = "2024-07-27T20:59:48Z",
            content = "Speaking to a crowd of supporters at the Bitcoin 2024 Conference in Nashville, Tennessee, former president and Republican nominee Donald Trump said he would make the U.S. the “crypto capital of the p… [+2326 chars]"
        ),
        Article(
            source = Source(id = "b", name = "Gizmodo.com"),
            author = "Passant Rabie",
            title = "Crypto Bro Charters Private SpaceX Mission to Earth’s Poles",
            description = "Fram2 could fly as early as this year, marking SpaceX's seventh private crew of astronauts.",
            url = "https://gizmodo.com/crypto-bro-charters-private-spacex-mission-to-earths-poles-2000486329",
            urlToImage = "https://gizmodo.com/app/uploads/2024/08/SpaceX-Private-Mission.jpeg",
            publishedAt = "2024-08-13T16:05:37Z",
            content = "A private crew of astronauts that includes a cinematographer and an explorer, and commanded by a wealthy bitcoin entrepreneur, will be the first human spaceflight to go over Earth’s polar region. Sp… [+2136 chars]"
        ),
        Article(
            source = Source(id = "c", name = "ReadWrite"),
            author = "Radek Zielinski",
            title = "Crypto asset firm Grayscale has lost $20 billion in Bitcoin and Ethereum ETFs",
            description = "Leading crypto-specific asset manager Grayscale Investments has lost over $20.4 billion through outflows from its Bitcoin (BTC) and Ethereum (ETH)… Continue reading Crypto asset firm Grayscale has lost $20 billion in Bitcoin and Ethereum ETFs\nThe post Crypto …",
            url = "https://readwrite.com/grayscale-bitcoin-ethereum-etf-losses-20-billion/",
            urlToImage = "https://readwrite.com/wp-content/uploads/2024/07/6f232dbd-27a0-45f2-87c6-639075c6ef40.webp",
            publishedAt = "2024-07-30T20:41:13Z",
            content = "Leading crypto-specific asset manager Grayscale Investments has lost over $20.4 billion through outflows from its Bitcoin (BTC) and Ethereum (ETH) exchange-traded funds (ETFs). According to data gat… [+2456 chars]"
        ),
        Article(
            source = Source(id = "d", name = "ReadWrite"),
            author = "Radek Zielinski",
            title = "US strategic Bitcoin reserve to be financed by revaluing Fed’s gold",
            description = "United States’ strategic Bitcoin (BTC) reserve would be partly financed by revaluing gold certificates held by the Federal Reserve System,… Continue reading US strategic Bitcoin reserve to be financed by revaluing Fed’s gold\nThe post US strategic Bitcoin rese…",
            url = "https://readwrite.com/us-strategic-bitcoin-reserve-to-be-financed-by-fed-gold/",
            urlToImage = "https://readwrite.com/wp-content/uploads/2024/07/b39a34b3-1c6a-47b1-ade2-fb55f8ae2510.webp",
            publishedAt = "2024-07-31T14:22:25Z",
            content = "United States’ strategic Bitcoin (BTC) reserve would be partly financed by revaluing gold certificates held by the Federal Reserve System, draft legislation shows. According to a July 30 Coindesk re… [+2038 chars]"
        ),
        Article(
            source = Source(id = "e", name = "ReadWrite"),
            author = "Radek Zielinski",
            title = "Donald Trump raises $25M during Bitcoin 2024 conference",
            description = "Republican presidential candidate Donald Trump raised $25 million during the The Bitcoin 2024 conference. Fox Business reporter Eleanor Terrett announced that… Continue reading Donald Trump raises $25M during Bitcoin 2024 conference\nThe post Donald Trump rais…",
            url = "https://readwrite.com/donald-trump-raises-25m-during-bitcoin-2024-conference/",
            urlToImage = "https://readwrite.com/wp-content/uploads/2024/07/21a7fe68-8fbd-44c2-8955-162a8d23f326.webp",
            publishedAt = "2024-07-31T11:57:00Z",
            content = "Republican presidential candidate Donald Trump raised $25 million during the The Bitcoin 2024 conference. Fox Business reporter Eleanor Terrett announced that Trump raised an undisclosed amount of f… [+1883 chars]"
        ),
        Article(
            source = Source(id = "f", name = "ReadWrite"),
            author = "Radek Zielinski",
            title = "Trump fights for the crypto vote at Bitcoin conference",
            description = "At the Bitcoin 2024 conference in Nashville, Tennessee, former President Donald Trump delivered a keynote speech. Trump, the Republican presidential… Continue reading Trump fights for the crypto vote at Bitcoin conference\nThe post Trump fights for the crypto …",
            url = "https://readwrite.com/trump-crypto-bitcoin-conference/",
            urlToImage = "https://readwrite.com/wp-content/uploads/2024/07/8483c1b4-7ea2-47a9-a0b2-b50d58578a99.webp",
            publishedAt = "2024-07-29T16:21:50Z",
            content = "At the Bitcoin 2024 conference in Nashville, Tennessee, former President Donald Trump delivered a keynote speech. Trump, the Republican presidential candidate, used the platform to appeal to the tec… [+3450 chars]"
        ),
        Article(
            source = Source(id = "g", name = "Wired"),
            author = "Jessica Klein",
            title = "Bitcoin Bros Go Wild for Donald Trump",
            description = "At Bitcoin 2024 conference in Nashville, Trump is finally telling crypto enthusiasts what they want to hear.",
            url = "https://www.wired.com/story/bitcoin-bros-go-wild-for-donald-trump/",
            urlToImage = "https://media.wired.com/photos/66a56f21bf2909f08a634953/191:100/w_1280,c_limit/Crypto-Bros-Business-2162975355.jpg",
            publishedAt = "2024-07-28T12:43:07Z",
            content = "Trumps speech is an hour behind. A half hour into the wait, restless attendees start chanting Trump. The woman sitting in front of me murmurs her own chant: Bitcoin, bitcointhats what they should be… [+3147 chars]"
        ),
    )

    val headlineDto = listOf(
        HeadlineDto(
            author = "author",
            content = "content",
            description = "description",
            publishedAt = "publishedAt",
            source = "source",
            title = "title",
            url = "url",
            urlToImage = "urlToImage",
            favourite = false,
            category = "category",
            page = 0,
        ), HeadlineDto(
            author = "author 1",
            content = "content 1",
            description = "description 1",
            publishedAt = "publishedAt 1",
            source = "source 1" ,
            title = "title 1",
            url = "url 1",
            urlToImage = "urlToImage 1",
            favourite = false,
            category = "category 1",
            page = 0,
        ), HeadlineDto(
            author = "author 2",
            content = "content 2",
            description = "description 2",
            publishedAt = "publishedAt 2",
            source = "source 2",
            title = "title 2",
            url = "url 2",
            urlToImage = "urlToImage 2",
            favourite = false,
            category = "category 2",
            page = 0,
        ),
        HeadlineDto(
            author = "author 3",
            content = "content 3",
            description = "description 3",
            publishedAt = "publishedAt 3",
            source = "source 3",
            title = "title 3",
            url = "url 3",
            urlToImage = "urlToImage 3",
            favourite = false,
            category = "category 3",
            page = 0,
        )
    )

    val newsyArticles = listOf(
        NewsyArticle(
            id = 1,
            author = "Author One",
            content = "Content of article one.",
            description = "Description of article one.",
            publishedAt = "2024-09-01T12:00:00Z",
            source = "Source One",
            title = "Title of Article One",
            url = "https://example.com/article1",
            urlToImage = "https://example.com/image1.jpg",
            favourite = false,
            category = "sports",
            page = 1
        ),
        NewsyArticle(
            id = 2,
            author = "Author Two",
            content = "Content of article two.",
            description = "Description of article two.",
            publishedAt = "2024-09-02T12:00:00Z",
            source = "Source Two",
            title = "Title of Article Two",
            url = "https://example.com/article2",
            urlToImage = "https://example.com/image2.jpg",
            favourite = true,
            category = "technology",
            page = 1
        ),
        NewsyArticle(
            id = 3,
            author = "Author Three",
            content = "Content of article three.",
            description = "Description of article three.",
            publishedAt = "2024-09-03T12:00:00Z",
            source = "Source Three",
            title = "Title of Article Three",
            url = "https://example.com/article3",
            urlToImage = "https://example.com/image3.jpg",
            favourite = false,
            category = "business",
            page = 1
        ),
        NewsyArticle(
            id = 4,
            author = "Author Four",
            content = "Content of article four.",
            description = "Description of article four.",
            publishedAt = "2024-09-04T12:00:00Z",
            source = "Source Four",
            title = "Title of Article Four",
            url = "https://example.com/article4",
            urlToImage = "https://example.com/image4.jpg",
            favourite = true,
            category = "health",
            page = 2
        ),
        NewsyArticle(
            id = 5,
            author = "Author Five",
            content = "Content of article five.",
            description = "Description of article five.",
            publishedAt = "2024-09-05T12:00:00Z",
            source = "Source Five",
            title = "Title of Article Five",
            url = "https://example.com/article5",
            urlToImage = "https://example.com/image5.jpg",
            favourite = false,
            category = "entertainment",
            page = 2
        ),
        NewsyArticle(
            id = 6,
            author = "Author Six",
            content = "Content of article six.",
            description = "Description of article six.",
            publishedAt = "2024-09-06T12:00:00Z",
            source = "Source Six",
            title = "Title of Article Six",
            url = "https://example.com/article6",
            urlToImage = "https://example.com/image6.jpg",
            favourite = true,
            category = "world",
            page = 2
        ),
        NewsyArticle(
            id = 7,
            author = "Author Seven",
            content = "Content of article seven.",
            description = "Description of article seven.",
            publishedAt = "2024-09-07T12:00:00Z",
            source = "Source Seven",
            title = "Title of Article Seven",
            url = "https://example.com/article7",
            urlToImage = "https://example.com/image7.jpg",
            favourite = false,
            category = "sports",
            page = 3
        ),
        NewsyArticle(
            id = 8,
            author = "Author Eight",
            content = "Content of article eight.",
            description = "Description of article eight.",
            publishedAt = "2024-09-08T12:00:00Z",
            source = "Source Eight",
            title = "Title of Article Eight",
            url = "https://example.com/article8",
            urlToImage = "https://example.com/image8.jpg",
            favourite = true,
            category = "health",
            page = 3
        ),
        NewsyArticle(
            id = 9,
            author = "Author Nine",
            content = "Content of article nine.",
            description = "Description of article nine.",
            publishedAt = "2024-09-09T12:00:00Z",
            source = "Source Nine",
            title = "Title of Article Nine",
            url = "https://example.com/article9",
            urlToImage = "https://example.com/image9.jpg",
            favourite = false,
            category = "technology",
            page = 3
        ),
        NewsyArticle(
            id = 10,
            author = "Author Ten",
            content = "Content of article ten.",
            description = "Description of article ten.",
            publishedAt = "2024-09-10T12:00:00Z",
            source = "Source Ten",
            title = "Title of Article Ten",
            url = "https://example.com/article10",
            urlToImage = "https://example.com/image10.jpg",
            favourite = true,
            category = "business",
            page = 4
        ),
        NewsyArticle(
            id = 11,
            author = "Author Eleven",
            content = "Content of article eleven.",
            description = "Description of article eleven.",
            publishedAt = "2024-09-11T12:00:00Z",
            source = "Source Eleven",
            title = "Title of Article Eleven",
            url = "https://example.com/article11",
            urlToImage = "https://example.com/image11.jpg",
            favourite = false,
            category = "world",
            page = 4
        ),
        NewsyArticle(
            id = 12,
            author = "Author Twelve",
            content = "Content of article twelve.",
            description = "Description of article twelve.",
            publishedAt = "2024-09-12T12:00:00Z",
            source = "Source Twelve",
            title = "Title of Article Twelve",
            url = "https://example.com/article12",
            urlToImage = "https://example.com/image12.jpg",
            favourite = true,
            category = "sports",
            page = 4
        ),
        NewsyArticle(
            id = 13,
            author = "Author Thirteen",
            content = "Content of article thirteen.",
            description = "Description of article thirteen.",
            publishedAt = "2024-09-13T12:00:00Z",
            source = "Source Thirteen",
            title = "Title of Article Thirteen",
            url = "https://example.com/article13",
            urlToImage = "https://example.com/image13.jpg",
            favourite = false,
            category = "entertainment",
            page = 5
        ),
        NewsyArticle(
            id = 14,
            author = "Author Fourteen",
            content = "Content of article fourteen.",
            description = "Description of article fourteen.",
            publishedAt = "2024-09-14T12:00:00Z",
            source = "Source Fourteen",
            title = "Title of Article Fourteen",
            url = "https://example.com/article14",
            urlToImage = "https://example.com/image14.jpg",
            favourite = true,
            category = "technology",
            page = 5
        ),
        NewsyArticle(
            id = 15,
            author = "Author Fifteen",
            content = "Content of article fifteen.",
            description = "Description of article fifteen.",
            publishedAt = "2024-09-15T12:00:00Z",
            source = "Source Fifteen",
            title = "Title of Article Fifteen",
            url = "https://example.com/article15",
            urlToImage = "https://example.com/image15.jpg",
            favourite = false,
            category = "health",
            page = 5
        ),
        NewsyArticle(
            id = 16,
            author = "Author Sixteen",
            content = "Content of article sixteen.",
            description = "Description of article sixteen.",
            publishedAt = "2024-09-16T12:00:00Z",
            source = "Source Sixteen",
            title = "Title of Article Sixteen",
            url = "https://example.com/article16",
            urlToImage = "https://example.com/image16.jpg",
            favourite = true,
            category = "business",
            page = 6
        ),
        NewsyArticle(
            id = 17,
            author = "Author Seventeen",
            content = "Content of article seventeen.",
            description = "Description of article seventeen.",
            publishedAt = "2024-09-17T12:00:00Z",
            source = "Source Seventeen",
            title = "Title of Article Seventeen",
            url = "https://example.com/article17",
            urlToImage = "https://example.com/image17.jpg",
            favourite = false,
            category = "entertainment",
            page = 6
        ),
        NewsyArticle(
            id = 18,
            author = "Author Eighteen",
            content = "Content of article eighteen.",
            description = "Description of article eighteen.",
            publishedAt = "2024-09-18T12:00:00Z",
            source = "Source Eighteen",
            title = "Title of Article Eighteen",
            url = "https://example.com/article18",
            urlToImage = "https://example.com/image18.jpg",
            favourite = true,
            category = "world",
            page = 6
        ),
        NewsyArticle(
            id = 19,
            author = "Author Nineteen",
            content = "Content of article nineteen.",
            description = "Description of article nineteen.",
            publishedAt = "2024-09-19T12:00:00Z",
            source = "Source Nineteen",
            title = "Title of Article Nineteen",
            url = "https://example.com/article19",
            urlToImage = "https://example.com/image19.jpg",
            favourite = false,
            category = "sports",
            page = 7
        ),
        NewsyArticle(
            id = 20,
            author = "Author Twenty",
            content = "Content of article twenty.",
            description = "Description of article twenty.",
            publishedAt = "2024-09-20T12:00:00Z",
            source = "Source Twenty",
            title = "Title of Article Twenty",
            url = "https://example.com/article20",
            urlToImage = "https://example.com/image20.jpg",
            favourite = true,
            category = "technology",
            page = 7
        )
    )

    fun formatPublishedAtDate(publishedAt: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMMM", Locale.getDefault())
        return try {
            val date = inputFormat.parse(publishedAt)
            outputFormat.format(date!!)
        } catch (e: Exception) {
            e.printStackTrace()
            "" // Handle parsing errors gracefully
        }
    }

}