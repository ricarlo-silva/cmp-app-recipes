package br.com.ricarlo.home.data.model

import br.com.ricarlo.home.domain.model.Fruit
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class FruitResponse(
    @SerialName("name") val name: String,
    @SerialName("id") val id: Long,
    @SerialName("family") val family: String,
    @SerialName("order") val order: String,
    @SerialName("genus") val genus: String,
    @SerialName("nutritions") val nutrition: NutritionResponse,
)

internal fun FruitResponse.toFruit() = Fruit(
    name = name,
    id = id,
    family = family,
    order = order,
    genus = genus,
    nutrition = nutrition.toNutrition()
)
