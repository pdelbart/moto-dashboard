package com.motodashboard

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import androidx.compose.runtime.mutableStateListOf

class NotificationListener : NotificationListenerService() {
    companion object {
        val activeNotificationsList = mutableStateListOf<String>()
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)
        sbn?.let {
            val packageName = it.packageName
            val extras = it.notification.extras
            val title = extras.getString("android.title") ?: "Notification"
            val text = extras.getCharSequence("android.text")?.toString() ?: ""
            
            val notifEntry = "$title: $text"
            if (!activeNotificationsList.contains(notifEntry)) {
                activeNotificationsList.add(0, notifEntry)
                if (activeNotificationsList.size > 20) {
                    activeNotificationsList.removeAt(activeNotificationsList.size - 1)
                }
            }
        }
    }
}
