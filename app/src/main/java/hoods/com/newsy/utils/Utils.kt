package hoods.com.newsy.utils

import hoods.com.newsy.R
import hoods.com.newsy.models.Country
import hoods.com.newsy.models.Language
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