package com.example.smacc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.ProgressBar
import com.example.smacc.utility.MakanNotifikasiUtils
import com.example.smacc.utility.PirNotifikasiUtils
import com.example.smacc.utility.lightStatusBar
import com.example.smacc.utility.setFullScreen

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val splashScreen: ImageView = findViewById(R.id.splash_screen)
        val progressBar: ProgressBar = findViewById(R.id.progressBar)

        splashScreen.alpha = 0f
        progressBar.alpha = 0f

        splashScreen.animate().setDuration(2000).alpha(1f).withEndAction {
            progressBar.animate().setDuration(1500).alpha(1f).withEndAction {
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                finish()
            }
        }

        // Memulai layanan PirNotifikasiUtils ketika aplikasi dibuka
        startService(Intent(this, PirNotifikasiUtils::class.java))

        // Panggil fungsi untuk memeriksa kondisi distance dan menampilkan notifikasi jika perlu
        val serviceIntent = Intent(this, MakanNotifikasiUtils::class.java)
        startService(serviceIntent)

        // Util untuk Fullscreen Activity
        setFullScreen(window)
        lightStatusBar(window)
    }
}