package br.com.ricarlo.home.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class Fruit(
    @SerialName("name") val name: String,
    @SerialName("id") val id: Long,
    @SerialName("family") val family: String,
    @SerialName("order") val order: String,
    @SerialName("genus") val genus: String,
    @SerialName("nutritions") val nutrition: Nutrition,
)
