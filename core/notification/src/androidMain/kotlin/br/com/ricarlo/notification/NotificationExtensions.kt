package br.com.ricarlo.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.content.IntentCompat
import com.google.firebase.messaging.RemoteMessage

fun Context.createPendingIntent(intent: Intent, requestCode: Int = 0): PendingIntent {
    return PendingIntent.getActivity(
        this.applicationContext,
        requestCode,
        intent,
        flags()
    )
}

fun Context.createBroadcastPendingIntent(intent: Intent, requestCode: Int = 0): PendingIntent {
    return PendingIntent.getBroadcast(
        this.applicationContext,
        requestCode,
        intent,
        flags()
    )
}

private fun flags() = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE

fun Context.createNotificationChannel(
    id: String,
    name: String,
    description: String? = null
) {
    val channel = NotificationChannel(
        id,
        name,
        NotificationManager.IMPORTANCE_DEFAULT
    ).apply {
        description?.let {
            this.description = it
        }
    }
    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(channel)
}

fun Context.areNotificationsEnabled(channelId: String? = null): Boolean {
    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    return when {
        !notificationManager.areNotificationsEnabled() -> {
            false
        }

        !channelId.isNullOrEmpty() -> {
            val channel = notificationManager.getNotificationChannel(channelId)
            channel.importance != NotificationManager.IMPORTANCE_NONE
        }

        else -> {
            notificationManager.notificationChannels.none { channel ->
                channel.importance == NotificationManager.IMPORTANCE_NONE
            }
        }
    }
}

fun Intent.getMessageData(): Map<String, Any> {
    val message = IntentCompat.getParcelableExtra(
        this, MESSAGE_KEY, RemoteMessage::class.java
    )
    return message?.data.orEmpty()
}
