package ru.kalistratov.breathtraining2.view.training.engine

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import ru.kalistratov.breathtraining2.R
import ru.kalistratov.breathtraining2.view.training.TrainingActivity


class EngineNotification(val context: Context) :
    NotificationCompat.Builder(context, CHANNEL_ID) {

    companion object {
        const val CHANNEL_ID = "training"
        const val CHANNEL_NAME = "Процесс тренировки"
        const val STOP_ENGINE = "engine.stop_training"
    }

    init {

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            notificationManager.createNotificationChannel(channel)
            setColorized(false)
            setChannelId(CHANNEL_ID)
            setBadgeIconType(NotificationCompat.BADGE_ICON_NONE)
        }
        setOnlyAlertOnce(true)
        setContentTitle(context.resources.getString(R.string.app_name))
        val notificationSound: Uri = RingtoneManager
            .getActualDefaultRingtoneUri(context, RingtoneManager.TYPE_NOTIFICATION)
        setSound(notificationSound)
        setAutoCancel(true)
        setSmallIcon(R.mipmap.ic_launcher)
        setStyle(NotificationCompat.BigTextStyle())
        setContentIntent(trainingActivityIntent())
        val deleteIntent = Intent(context, TrainingEngineService::class.java)
        deleteIntent.action = javaClass.name
        deleteIntent.putExtra("action", STOP_ENGINE)
        val deletePendingIntent = PendingIntent.getService(context, 0, deleteIntent, 0)
        addAction(android.R.drawable.ic_delete, "Stop", deletePendingIntent)
    }

    private fun trainingActivityIntent(): PendingIntent {
        val intent = Intent(context, TrainingActivity::class.java)
        return PendingIntent.getActivity(
            context,
            0 /* Request code */,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }
}