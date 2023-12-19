package com.example.smacc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.google.firebase.database.*

class DashboardActivity : AppCompatActivity() {

    private lateinit var cardView: CardView
    private lateinit var imageView: ImageView
    private lateinit var textviewUmurAyam: TextView
    private lateinit var databaseRef: DatabaseReference
    private lateinit var firebaseDatabase: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Util untuk Fullscreen Activity
        // setFullScreen(window)
        // lightStatusBar(window)

        // Menampilkan data umur dari ayam
        // Inisialisasi TextView umur ayam
        textviewUmurAyam = findViewById(R.id.textViewUmurAyam)

        // Inisialisasi Firebase Database Realtime dan mendapatkan referensi ke node yang berisi string
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseRef = firebaseDatabase.getReference("DataSensor")

        // Membuat listener untuk mendegar perubahan pada data
        databaseRef.child("keterangan").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Mendapatkan dan menampilkan data "keterangan" dalam TextView dari realtime database
                val textUmur =  dataSnapshot.getValue(String::class.java)
                textviewUmurAyam.text = textUmur
            }

            override fun onCancelled(error: DatabaseError) {
                // Menangani jika terjadi error
                textviewUmurAyam.text = "Error"
            }
        })

        // CardView Monitoring Kandang One
        cardView = findViewById(R.id.cardViewMonitoring)
        cardView.setOnClickListener {
            val intent = Intent(this, MonitoringActivity::class.java)
            startActivity(intent)
            //Toast.makeText(this, "CardView 1 diklik", Toast.LENGTH_SHORT).show()
        }

        // CardView Controlling Kandang One
        cardView = findViewById(R.id.cardViewControlling)
        cardView.setOnClickListener {
            val intent = Intent(this, ControllingActivity::class.java)
            startActivity(intent)
            //Toast.makeText(this,"Controlling Clicked", Toast.LENGTH_SHORT).show()
        }

        // CardView Guide Kandang One
        cardView = findViewById(R.id.cardViewGuide)
        cardView.setOnClickListener {
            val intent = Intent(this, GuideActivity::class.java)
            startActivity(intent)
            // Toast.makeText(this,"Guide Clicked", Toast.LENGTH_SHORT).show()
        }

        // CardView About Kandang One
        cardView = findViewById(R.id.cardViewAbout)
        cardView.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
            // Toast.makeText(this,"About Clicked", Toast.LENGTH_SHORT).show()
        }

        // Button Back
        imageView = findViewById(R.id.button_back)
        imageView.setOnClickListener {
            onBackPressed()
        }
    }
}