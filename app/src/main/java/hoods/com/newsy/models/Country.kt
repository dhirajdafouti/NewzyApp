package hoods.com.newsy.models

import androidx.annotation.DrawableRes

data class Country(
    val code: String,
    val name: String,
    @DrawableRes val icResId: Int,
)