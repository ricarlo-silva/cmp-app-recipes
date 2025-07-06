package br.com.ricarlo.home.domain.repository

import br.com.ricarlo.home.domain.model.Fruit

internal interface HomeRepository {
    suspend fun getFruits(): List<Fruit>
}
