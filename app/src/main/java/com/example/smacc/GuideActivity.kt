package com.example.smacc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

class GuideActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var faqAdapter: FAQAdapter
    private lateinit var buttonBack: ImageView
    private lateinit var searchView: SearchView
    private lateinit var emptyView : TextView

    // Daftar pertanyaan dan jawaban FAQ yang akan ditampilkan
    private val faqList = mutableListOf<FAQData.FAQItem>()
    private val filteredFaqList = mutableListOf<FAQData.FAQItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide)

        // Inisialisasi tampilan
        recyclerView = findViewById(R.id.recyclerViewFAQ)
        buttonBack = findViewById(R.id.button_back)
        searchView = findViewById(R.id.searchView)
        emptyView = findViewById(R.id.emptyView)

        // Konfigurasi tampilan daftar pertanyaan FAQ
        recyclerView.layoutManager = LinearLayoutManager(this)
        faqAdapter = FAQAdapter(faqList)
        recyclerView.adapter = faqAdapter

        // Menangani tombol kembali
        buttonBack.setOnClickListener {
            onBackPressed()
        }

        // Menyiapkan fungsi pencarian
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            // Dipanggil saat pengguna menekan tombol "Submit" pada keyboard
            override fun onQueryTextSubmit(query: String): Boolean {
                filterFaqList(query)
                return false
            }

            // Dipanggil saat teks dalam SearchView berubah
            override fun onQueryTextChange(newText: String): Boolean {
                filterFaqList(newText)
                return false
            }
        })

        // Memuat data FAQ dari file JSON
        loadFAQData()
    }

    // Fungsi untuk memfilter daftar FAQ berdasarkan kueri
    private fun filterFaqList(query: String) {
        filteredFaqList.clear()

        // Jika kueri kosong, menambahkan semua item FAQ ke dalam daftar yang difilter
        if (query.isEmpty()) {
            filteredFaqList.addAll(faqList)
        } else {
            for (faqItem in faqList) {
                // Memeriksa apakah pertanyaan item FAQ mengandung kueri
                if (faqItem.pertanyaan.contains(query, ignoreCase = true)) {
                    // Jika pertanyaan cocok dengan kueri, menambahkan item FAQ ke daftar yang difilter
                    filteredFaqList.add(faqItem)
                }
            }
        }

        // Mengatur tampilan berdasarkan hasil filter
        if (filteredFaqList.isEmpty()) {
            // Jika tidak ada hasil, menampilkan teks kosongView dan menyembunyikan recyclerView
            emptyView.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            // Jika ada hasil, menyembunyikan teks kosongView dan menampilkan recyclerView
            emptyView.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }

        // Memperbarui adapter dengan daftar yang difilter
        faqAdapter.updateList(filteredFaqList)
    }

    private fun loadFAQData() {
        val json: String = loadJSONFromAsset("faq_data.json")
        val faqData = Gson().fromJson(json, FAQData::class.java)

        // Menampilkan data yang berhasil di Parsing ke Log
        for (faqItem in faqData.faq_list_data) {
            Log.d("FAQ", "Pertanyaan: ${faqItem.pertanyaan}")
            Log.d("FAQ", "Jawaban: ${faqItem.jawaban}")
        }

        faqList.addAll(faqData.faq_list_data)
        faqAdapter.notifyDataSetChanged()
    }



    private fun loadJSONFromAsset(fileName: String): String {
        val json: String?
        try {
            val inputStream: InputStream = assets.open(fileName)
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            json = bufferedReader.use { it.readText() }
        } catch (ex: IOException) {
            ex.printStackTrace()
            return ""
        }
        return json
    }
}
