package br.com.ricarlo.common

enum class RemoteConfigKey(val key: String) {
    GOOGLE_LOGIN_ENABLED("google_login_enabled"),
    WELCOME_MESSAGE("welcome_message");

    companion object {
        fun defaultConfig(): Map<String, Any> = mapOf(
            GOOGLE_LOGIN_ENABLED.key to false,
            WELCOME_MESSAGE.key to "Welcome"
        )
    }
}
