package br.com.ricarlo.common

import androidx.navigation.NavUri
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal const val NOTIFICATION_URI_KEY = "uri"

interface IDeepLinkHandler {
    fun processDeepLink(uri: String?)
    fun processMessage(remoteMessage: Map<String, Any>)
}

internal class DeepLinkHandler(
    private val scope: CoroutineScope
) : IDeepLinkHandler {

    override fun processDeepLink(uri: String?) {
        if (uri.isNullOrEmpty()) return
        scope.launch {
            EventBus.send(event = NavUri(uri))
        }
    }

    override fun processMessage(remoteMessage: Map<String, Any>) {
        if (remoteMessage.isEmpty()) return
        val uri = remoteMessage[NOTIFICATION_URI_KEY] as? String
        processDeepLink(uri = uri)
    }
}
