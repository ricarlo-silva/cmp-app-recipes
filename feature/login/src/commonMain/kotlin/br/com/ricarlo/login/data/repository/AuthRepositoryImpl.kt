package br.com.ricarlo.login.data.repository

import br.com.ricarlo.login.domain.repository.AuthRepository
import kotlinx.coroutines.delay

internal class AuthRepositoryImpl : AuthRepository {
    override suspend fun login(username: String, password: String) {
        // TODO: Implement login logic
        delay(1000)
    }

    override suspend fun signup(username: String, password: String) {
        // TODO: Implement signup logic
    }

    override suspend fun forgotPassword(username: String) {
        // TODO: Implement forgot password logic
    }
}
