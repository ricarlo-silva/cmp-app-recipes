package br.com.ricarlo.home.data.remote

import br.com.ricarlo.home.data.model.FruitResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

internal interface ApiHome {
    suspend fun getFruits(): List<FruitResponse>
}

internal class ApiHomeImpl(
    private val httpClient: HttpClient
) : ApiHome {
    override suspend fun getFruits(): List<FruitResponse> {
        // https://github.com/public-apis/public-apis?tab=readme-ov-file
        return httpClient
            .get("https://www.fruityvice.com/api/fruit/all")
            .body()
    }
}
