package com.example.smacc.utility

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class DeleteNotification : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        // Jika aksi "Hapus" pada notifikasi diterima, batalkan notifikasi yang sesuai
        val notificationManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = intent?.getIntExtra(NOTIFICATION_ID_EXTRA, 0)
        notificationManager.cancel(notificationId ?: 0)
    }

    companion object {
        const val NOTIFICATION_ID_EXTRA = "notification_id_extra"
    }
}
