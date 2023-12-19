package com.example.smacc.utility

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.smacc.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*

class PirNotifikasiUtils : Service() {

    // Inisialisasi DatabaseReference untuk akses ke "pirNotif" di Firebase Realtime Database
    private val pirNotifRef = FirebaseDatabase.getInstance().reference.child("pirNotif")

    // notification ketika PIR mendeteksi gerakan
    private var NOTIFICATION_CHANNEL_ID = "MotionDetectionChannel"
    private var NOTIFICATION_ID = 1
    private val timer = Timer()
    private var notificationInterval = 10000L // Waktu dalam milidetik (10000 ms = 10 detik)


    /**
     * Funtion onStartCommand dipanggil ketika layanan dimulai.
     * Function ini berfungsi untuk memulai pemantauan perubahan nilai pirNotif di Realtime Database
     *
     * @param intent Intent yang digunakan untuk memulai layanan.
     * @param flags Flag untuk layanan.
     * @param startId ID layanan yang dimulai.
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Memantau perubahan nilai pirNotif di Firebase
        pirNotifRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Ambil data dari snapshot
                val pirNotif = snapshot.getValue(Boolean::class.java)
                if (pirNotif ==  true) {
                    // Memanggil funtion untuk menampikan notifikasi ketika ada deteksi gerakan
                    showAutomaticNotification()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        // Metode ini akan membuat layanan yang dijalankan kembali (START_STICKY) jika dipaksa berhenti oleh sistem
        return START_STICKY
    }


    /**
     * Metode untuk menampilkan notifikasi secara otomatis dengan interval waktu.
     * Notifikasi akan ditampilkan berdasarkan waktu yang telah ditentukan.
     */
    private fun showAutomaticNotification() {
        // Update nilai pirNotif di database ketika notifikasi muncul
        pirNotifRef.setValue(true)

        // Memanggil function sendNotification() untuk mengirim notifikasi
        sendNotification()

        // Fungsi untuk menampilkan notifikasi secara otomatis dengan interval waktu
        timer.schedule(object : TimerTask() {
            override fun run() {
                // Update nilai pirNotif ke false setelah pemberitahuan ditampilkan untuk interval yang ditentukan
                pirNotifRef.setValue(false)
            }
        }, notificationInterval)
    }

    /**
     * Metode untuk mengirim notifikasi ke sistem Android untuk ditampilkan di bilah notifikasi.
     */
    private fun sendNotification() {
        // Builder notifikasi
        val builder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("SCCOOP")
            .setContentText("Sensor PIR mendeteksi ada gerakan mencurigakan!!!")
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)

        // Menampilkan notifikasi
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel(notificationManager)
        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

    /**
     * Metode untuk membuat saluran notifikasi (Notification Channel) jika perangkat menggunakan Android Oreo (26) atau di atasnya.
     *
     * @param notificationManager digunakan untuk membuat saluran.
     */
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        // Hanya perlu dibuat pada Android Oreo (26) dan di atasnya
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = "Motion Detection"
            val channelDescription = "Channel untuk notifikasi deteksi gerakan"
            val importance = NotificationManager.IMPORTANCE_HIGH

            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, importance)
            channel.description = channelDescription

            notificationManager.createNotificationChannel(channel)
        }
    }

    /**
     * Metode onBind dipanggil ketika layanan terikat (bind) ke komponen lain (seperti Activity).
     * Layanan ini tidak diikatkan, maka mengembalikan null.
     */
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}