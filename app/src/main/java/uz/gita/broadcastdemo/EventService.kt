package uz.gita.broadcastdemo

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

class EventService : Service() {

    private var receiver: ScreenStateBroadcastReceiver? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()

        createChannel()
        var id = 123
        val notification = NotificationCompat.Builder(this, "Event").apply {
            setSmallIcon(R.drawable.ic_launcher_foreground)
            setContentTitle("Event App")
            setContentText("This app listen events from System")
            setContentIntent(
                PendingIntent.getActivity(
                    this@EventService,
                    0,
                    Intent(this@EventService, MainActivity::class.java),
                    PendingIntent.FLAG_IMMUTABLE
                )
            )
        }.build()
        startForeground(id, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (receiver == null) {
            receiver = ScreenStateBroadcastReceiver()
        }
        registerReceiver(
            receiver,
            IntentFilter().apply {
                addAction(Intent.ACTION_SCREEN_ON)
                addAction(Intent.ACTION_SCREEN_OFF)
                addAction(Intent.ACTION_POWER_CONNECTED)
                addAction(Intent.ACTION_POWER_DISCONNECTED)
                addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
                addAction(Intent.ACTION_BATTERY_LOW)
                addAction(Intent.ACTION_BATTERY_OKAY)
            }
        )
        return START_STICKY
    }

    override fun onDestroy() {
        if (receiver != null) unregisterReceiver(receiver)
        super.onDestroy()
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                "Event",
                "Main",
                NotificationManager.IMPORTANCE_HIGH
            )

            val notificationManager: NotificationManager =
                getSystemService(NotificationManager::class.java)

            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

}