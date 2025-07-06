package br.com.ricarlo.home.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class Nutrition(
    @SerialName("calories") val calories: Long,
    @SerialName("fat") val fat: Double,
    @SerialName("sugar") val sugar: Double,
    @SerialName("carbohydrates") val carbohydrates: Double,
    @SerialName("protein") val protein: Double
)
