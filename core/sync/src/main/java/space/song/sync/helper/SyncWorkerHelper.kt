package space.song.sync.helper

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Constraints
import androidx.work.ForegroundInfo

private const val SYNC_NOTIFICATION_ID = 0
private const val SYNC_NOTIFICATION_CHANNEL_ID = "SyncNotificationChannel"

val SyncConstraints
    get() = Constraints.Builder()
        .setRequiredNetworkType(androidx.work.NetworkType.CONNECTED)
        .build()

fun Context.syncForegroundInfo() = ForegroundInfo(
    SYNC_NOTIFICATION_ID,
    syncWorkNotification()

)

private fun Context.syncWorkNotification(): Notification {
    val channel = NotificationChannel(
        SYNC_NOTIFICATION_CHANNEL_ID,
        "Sync Work ...",
        NotificationManager.IMPORTANCE_DEFAULT,
    ).apply {
        description = "Sync Work ..."
    }
    // Register the channel with the system
    val notificationManager: NotificationManager? =
        getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager

    notificationManager?.createNotificationChannel(channel)

    return NotificationCompat.Builder(
        this,
        SYNC_NOTIFICATION_CHANNEL_ID,
    )
        //.setSmallIcon()
        .setContentTitle("Rule the World")
        .setContentText("Sync Work ...")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .build()
}
