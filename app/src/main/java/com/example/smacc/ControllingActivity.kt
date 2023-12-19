package com.example.smacc

import android.os.Bundle
import android.widget.CompoundButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ControllingActivity : AppCompatActivity() {

    private lateinit var switchButtonLamp: SwitchCompat
    private lateinit var switchButtonPir: SwitchCompat
    private lateinit var imagaeViewLight: ImageView
    private lateinit var imageViewPir: ImageView
    lateinit var imageView: ImageView

    // Inisialisasi DatabaseReference
    private val lampuStateRef = FirebaseDatabase.getInstance().reference.child("lampuState")
    private val pirStateRef = FirebaseDatabase.getInstance().reference.child("pirState")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_controlling)

        // Update data lampuStateRef untuk menyalakan atau mematikan lampu
        lampuStateRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lampuState = snapshot.getValue(Boolean::class.java)
                switchButtonLamp.isChecked = lampuState == true
            }

            override fun onCancelled(error: DatabaseError) {
                // Tangani error jika terjadi
            }
        })

        // Button Lampu
        switchButtonLamp = findViewById(R.id.SwitchButtonLamp)
        imagaeViewLight = findViewById(R.id.imageViewLamp)

        switchButtonLamp.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, b ->
            if (compoundButton.isChecked) {
                imagaeViewLight.setImageResource(R.drawable.lamp_controlling_on)
                lampuStateRef.setValue(true)
            } else {
                imagaeViewLight.setImageResource(R.drawable.lamp_controlling_off)
                lampuStateRef.setValue(false)
            }
        })

        // Update data pirState untuk menyalakan atau mematikan pir
        pirStateRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val pirState = snapshot.getValue(Boolean::class.java)
                switchButtonPir.isChecked = pirState == true
            }

            override fun onCancelled(error: DatabaseError) {
                // Tangani error jika terjadi
            }
        })

        // Button Pir
        switchButtonPir = findViewById(R.id.SwitchButtonPir)
        imageViewPir = findViewById(R.id.imageViewPir)

        switchButtonPir.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, b ->
            if (compoundButton.isChecked) {
                imageViewPir.setImageResource(R.drawable.on_lock_pir)
                // Toast.makeText(this,"Keamanan Menyala", Toast.LENGTH_SHORT).show()
                pirStateRef.setValue(true)
            } else {
                imageViewPir.setImageResource(R.drawable.off_lock_pir)
                // Toast.makeText(this,"Keamanan Mati", Toast.LENGTH_SHORT).show()
                pirStateRef.setValue(false)
            }
        })

        // Button Back
        imageView = findViewById(R.id.button_back)
        imageView.setOnClickListener {
            onBackPressed()
        }
    }
}
