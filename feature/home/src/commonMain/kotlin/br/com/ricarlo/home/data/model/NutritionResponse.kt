package br.com.ricarlo.home.data.model

import br.com.ricarlo.home.domain.model.Nutrition
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class NutritionResponse(
    @SerialName("calories") val calories: Long,
    @SerialName("fat") val fat: Double,
    @SerialName("sugar") val sugar: Double,
    @SerialName("carbohydrates") val carbohydrates: Double,
    @SerialName("protein") val protein: Double
)

internal fun NutritionResponse.toNutrition() = Nutrition(
    calories = calories,
    fat = fat,
    sugar = sugar,
    carbohydrates = carbohydrates,
    protein = protein
)
