package br.com.ricarlo.login.domain.repository

interface AuthRepository {
    suspend fun login(username: String, password: String)
    suspend fun signup(username: String, password: String)
    suspend fun forgotPassword(username: String)
}
