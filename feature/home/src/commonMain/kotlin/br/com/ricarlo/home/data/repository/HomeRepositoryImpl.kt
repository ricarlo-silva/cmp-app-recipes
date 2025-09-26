package br.com.ricarlo.home.data.repository

import br.com.ricarlo.home.data.model.toFruit
import br.com.ricarlo.home.data.remote.ApiHome
import br.com.ricarlo.home.domain.model.Fruit
import br.com.ricarlo.home.domain.repository.HomeRepository

internal class HomeRepositoryImpl(
    private val apiHome: ApiHome
) : HomeRepository {
    override suspend fun getFruits(): List<Fruit> {
        return apiHome.getFruits().map { it.toFruit() }
    }
}
