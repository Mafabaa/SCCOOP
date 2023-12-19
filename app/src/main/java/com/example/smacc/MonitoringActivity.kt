package com.example.smacc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class MonitoringActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var myAdapter: AdapterMonitoring
    private lateinit var databaseRef: DatabaseReference
    private lateinit var imageView: ImageView
    private lateinit var valueEventListener: ValueEventListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_monitoring)

        // Utility Navigtion
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            window.navigationBarColor = ContextCompat.getColor(this, R.color.cream_color)
//        }

        // Menginisialisasi RecyclerView
        recyclerView = findViewById(R.id.recyclerViewMonitoring)
        recyclerView.layoutManager = LinearLayoutManager(this)
        myAdapter = AdapterMonitoring(listOf())
        recyclerView.adapter = myAdapter

        // Mendapatkan referensi database Firebase
        databaseRef = FirebaseDatabase.getInstance().reference.child("DataSensor")

        // Mendefinisikan ValueEventListener
        valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val sensorDataList = mutableListOf<DataSensor>()

                // Mengambil nilai keterangan dari DataSnapshot
                val keterangan = snapshot.child("keterangan").getValue(String::class.java)

                // Mengambil data suhu
                val suhu = snapshot.child("suhu").getValue(Float::class.java)
                if (suhu != null) {
                    val suhuData = Suhu(suhu, keterangan.orEmpty()) // Melewati nilai keterangan sebagai parameter
                    sensorDataList.add(suhuData)
                }

                // Mengambil data kelembapan
                val kelembapan = snapshot.child("kelembapan").getValue(Float::class.java)
                if (kelembapan != null) {
                    val kelembapanData = Kelembapan(kelembapan, keterangan.orEmpty())
                    sensorDataList.add(kelembapanData)
                }

                // Mengambil data amonia
                val amonia = snapshot.child("amonia").getValue(Float::class.java)
                if (amonia != null) {
                    val amoniaData = Amonia(amonia, keterangan.orEmpty())
                    sensorDataList.add(amoniaData)
                }

                // Mengambil data distance / ultrasonik
                val distance = snapshot.child("distance").getValue(Int::class.java)
                if (distance != null) {
                    val distanceData = Distance(distance, keterangan.orEmpty())
                    sensorDataList.add(distanceData)
                }

                // Mengambil data waterlvel
                val waterlevel = snapshot.child("waterlevel").getValue(Int::class.java)
                if (waterlevel != null) {
                    val waterlevelData = Waterlevel(waterlevel, keterangan.orEmpty())
                    sensorDataList.add(waterlevelData)
                }

                // Update data pada myAdapter
                myAdapter.updateData(sensorDataList)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        }

        // Menambahkan ValueEventListener ke DatabaseReference
        databaseRef.addValueEventListener(valueEventListener)

        // Button Back
        imageView = findViewById(R.id.button_back)
        imageView.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Menghapus ValueEventListener saat activity dihancurkan
        databaseRef.removeEventListener(valueEventListener)
    }

}
