package com.example.youtubeklonapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters


class APIWorker(context: Context,workerParameters: WorkerParameters): Worker(context,workerParameters) {


    override fun doWork(): Result {
        //createNotification("Background Task", "This notification is generated by workManager")
        return Result.success()
    }
    /*private fun createNotification(title: String, description: String) {

        var notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel("101", "channel", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        val notificationBuilder = NotificationCompat.Builder(applicationContext, "101")
            .setContentTitle(title)
            .setContentText(description)
            .setSmallIcon(R.drawable.ic_launcher_background)

        notificationManager.notify(1, notificationBuilder.build())

    }*/
}