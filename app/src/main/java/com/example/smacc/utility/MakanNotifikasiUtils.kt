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
import com.google.firebase.database.*

class MakanNotifikasiUtils : Service() {

    // Inisialisasi DatabaseReference untuk akses ke "distance" di Firebase Realtime Database
    private val distanceRef = FirebaseDatabase.getInstance().reference.child("DataSensor").child("distance")

    // notification ketika makanan habis
    private val NOTIFICATION_CHANNEL_ID = "FoodEmptyChannel"
    private val NOTIFICATION_ID = 2

    /**
     * Fungsi onStartCommand dipanggil ketika layanan dimulai.
     * Fungsi ini berfungsi untuk memulai pemantauan perubahan nilai distance di Realtime Database.
     *
     * @param intent Intent yang digunakan untuk memulai layanan.
     * @param flags Flag untuk layanan.
     * @param startId ID layanan yang dimulai.
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Memantau perubahan nilai distance di Firebase
        distanceRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Ambil data dari snapshot
                val distanceValue = snapshot.getValue(Int::class.java)

                // Memanggil fungsi untuk menampilkan notifikasi berdasarkan nilai distance
                if (distanceValue == 1) {
                    showNotification("Makanan Habis", "Persiapkan makanan baru!")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error jika ada
            }
        })
        // Metode ini akan membuat layanan yang dijalankan kembali (START_STICKY) jika dipaksa berhenti oleh sistem
        return START_STICKY
    }

    /**
     * Fungsi untuk menampilkan notifikasi.
     *
     * @param title Judul notifikasi.
     * @param content Isi dari notifikasi.
     */
    private fun showNotification(title: String, content: String) {
        // Buat saluran notifikasi (hanya diperlukan untuk Android versi 8.0 ke atas)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "Food Empty Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        // Bangun notifikasi
        val builder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        // Tampilkan notifikasi
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

    /**
     * Fungsi onBind dipanggil ketika layanan terikat (bind) ke komponen lain (seperti Activity).
     * Layanan ini tidak diikatkan, maka mengembalikan null.
     */
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
