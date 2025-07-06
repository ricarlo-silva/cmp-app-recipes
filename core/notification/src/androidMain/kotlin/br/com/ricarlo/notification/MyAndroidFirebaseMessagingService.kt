package br.com.ricarlo.notification

import android.app.NotificationManager
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import br.com.ricarlo.notification.core.IFcmHandler
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal const val MESSAGE_KEY = "message"
internal const val NOTIFICATION_TITLE_KEY = "title"
internal const val NOTIFICATION_BODY_KEY = "body"
internal const val NOTIFICATION_URI_KEY = "uri"

internal class MyAndroidFirebaseMessagingService : FirebaseMessagingService(), KoinComponent {

    private val fcmHandler by inject<IFcmHandler>()

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        fcmHandler.onNewToken(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        fcmHandler.onMessageReceived(remoteMessage.data)

        showNotification(remoteMessage)
    }

    private fun showNotification(message: RemoteMessage) {
        val intent = Intent(
            Intent.ACTION_VIEW,
            message.data[NOTIFICATION_URI_KEY].orEmpty().toUri()
        ).apply {
            putExtra(MESSAGE_KEY, message)
        }

        val channelId = getString(R.string.default_notification_channel_id)
        val notificationBuilder = NotificationCompat.Builder(this.applicationContext, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(message.data[NOTIFICATION_TITLE_KEY].orEmpty())
            .setContentText(message.data[NOTIFICATION_BODY_KEY].orEmpty())
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(createPendingIntent(intent))

        createNotificationChannel(
            id = channelId,
            name = getString(R.string.default_notification_channel_name),
            description = getString(R.string.default_notification_channel_description)
        )

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(
            message.messageId.orEmpty().hashCode(),
            notificationBuilder.build()
        )
    }
}
