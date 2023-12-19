package com.example.smacc

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.example.smacc.utility.lightStatusBar
import com.example.smacc.utility.setFullScreen
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var cardView: CardView
    private lateinit var cardViewSendData: CardView
    private var dataTanggalDikirim = false

    private val PREF_NAME = "MyPrefs"
    private val KEY_LAST_SYNC_TIME = "lastSyncTime"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Util untuk Fullscreen Activity
        setFullScreen(window)
        lightStatusBar(window)

        cardView = findViewById(R.id.cardViewStart)
        cardView.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
            //Toast.makeText(this, "CardView 1 diklik", Toast.LENGTH_SHORT).show()
        }

        // Setting Refrence node Firebase
        val databaseReference = FirebaseDatabase.getInstance().reference.child("dateStart")

        // Inisialisasi Cardview dan variabel clickable
        cardViewSendData = findViewById(R.id.cardViewSendDate)
        cardViewSendData.setOnClickListener {
            if (!dataTanggalDikirim) {
                // Cek waktu terakhir pengiriman tanggal dari Shared Preferences
                val lastSyncTime = getLastSyncTime()

                // Waktu saat ini
                val currentTimeMillis = System.currentTimeMillis()

                // Interval Waktu (dalam milidetik)
                val intervalWaktu = 30 * 1000

                if (currentTimeMillis - lastSyncTime >= intervalWaktu) {
                    // Waktu sudah melewati batas interval, lakukan pengiriman tanggal
                    val calendar = Calendar.getInstance()

                    cardViewSendData.isEnabled = false
                    cardViewSendData.setCardBackgroundColor(ContextCompat.getColor(this, R.color.grey_color))

                    dataTanggalDikirim = true

                    val currentDate = calendar.get(Calendar.DAY_OF_MONTH) // dalam satuan hari dalam bulam
                    val currentHour = calendar.get(Calendar.HOUR_OF_DAY) // dalam satuan jam dalam hari
                    val currentMinute = calendar.get(Calendar.MINUTE) // dalam satuan menit

                    // Mengirim tanggal ke Firebase Realtime
                    databaseReference.setValue(currentDate)
                        .addOnSuccessListener {
                            // Berhasil Mengirim Data
                            Snackbar.make(cardViewSendData, "Berhasil Sinkronisasi Data", Snackbar.LENGTH_LONG
                            ).show()
                            dataTanggalDikirim = true

                        }
                        .addOnFailureListener {
                            // Gagal Mengirim Data
                            Snackbar.make(cardViewSendData, "Gagal Sinkronisasi Data", Snackbar.LENGTH_LONG
                            ).show()

                            // Kembalikan tombol menjadi aktif dan warna semula karena gagal mengirim data
                            cardViewSendData.isEnabled = true
                            cardViewSendData.setBackgroundColor(Color.WHITE)
                            dataTanggalDikirim = false
                        }

                    // Simpan waktu terakhir pengiriman tanggal ke Shared Preferences
                    saveLastSyncTime(currentTimeMillis)
                }
            }
        }

        // Cek waktu terakhir pengiriman tanggal dari Shared Preferences saat aplikasi dibuka kembali
        val lastSyncTime = getLastSyncTime()
        val currentTime = System.currentTimeMillis()
        val intervalWaktu = 30 * 1000

        if (currentTime - lastSyncTime < intervalWaktu) {
            // Jika waktu terakhir pengiriman masih dalam interval waktu, maka tombol tetap tidak aktif
            cardViewSendData.isEnabled = false
            cardViewSendData.setCardBackgroundColor(ContextCompat.getColor(this, R.color.grey_color))
        } else {
            // Jika waktu terakhir pengiriman telah melewati interval waktu, tombol dapat diaktifkan kembali
            cardViewSendData.isEnabled = true
            cardViewSendData.setCardBackgroundColor(ContextCompat.getColor(this, R.color.white))
        }
    }
    // Function untuk mendapatkan waktu terakhir pengiriman tanggal dari Shared Preferences
    private fun getLastSyncTime(): Long {
        val sharedPref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPref.getLong(KEY_LAST_SYNC_TIME, 0)
    }

    // Function untuk menyimpan waktu terakhir pengiriman tanggal ke Shared Preferences
    private fun saveLastSyncTime(time: Long) {
        val sharedPref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putLong(KEY_LAST_SYNC_TIME, time)
        editor.apply()
    }
}