package com.dicoding.todoapp.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.dicoding.todoapp.R
import com.dicoding.todoapp.data.Task
import com.dicoding.todoapp.data.TaskRepository
import com.dicoding.todoapp.ui.detail.DetailTaskActivity
import com.dicoding.todoapp.utils.TimeConverter
import com.dicoding.todoapp.utils.NOTIFICATION_CHANNEL_ID
import com.dicoding.todoapp.utils.TASK_ID

class NotificationWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

    private val channelName = inputData.getString(NOTIFICATION_CHANNEL_ID)

    private fun getPendingIntent(task: Task): PendingIntent? {
        val intent = Intent(applicationContext, DetailTaskActivity::class.java).apply {
            putExtra(TASK_ID, task.id)
        }
        return TaskStackBuilder.create(applicationContext).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }
    }

    override fun doWork(): Result {
        // 14 : If notification preference on, get nearest active task from repository and show notification with pending intent
        val nearestStartTask = TaskRepository.getInstance(applicationContext).getNearestStartTask()
        val mNotificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val mBuilder = channelName?.let {
            NotificationCompat.Builder(applicationContext, it)
                .setContentIntent(getPendingIntent(nearestStartTask))
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentTitle(nearestStartTask.title)
                .setContentText(applicationContext.getString(R.string.notify_content, TimeConverter.convertMillisToString(nearestStartTask.startTimeMillis)))
                .setAutoCancel(true)
        }

        /*
        Untuk android Oreo ke atas perlu menambahkan notification channel
        */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            /* Create or update. */
            val channel = NotificationChannel(channelName, channelName, NotificationManager.IMPORTANCE_DEFAULT)

            if (channelName != null) {
                mBuilder?.setChannelId(channelName)
            }

            mNotificationManager.createNotificationChannel(channel)
        }

        val notification = mBuilder?.build()

        mNotificationManager.notify(1, notification)
        return Result.success()
    }

}
