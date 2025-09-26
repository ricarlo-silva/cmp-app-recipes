package br.com.ricarlo.network

interface TokenManager {
    fun getToken(): String
    fun getRefreshToken(): String
}

// TODO: Load tokens from a local storage
internal class TokenManagerImpl : TokenManager {
    override fun getToken(): String {
        return BuildConfig.API_KEY
    }

    override fun getRefreshToken(): String {
        return "xyz111"
    }
}
