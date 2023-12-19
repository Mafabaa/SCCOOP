package com.example.smacc.utility

import com.example.smacc.WeeklyHumidityRange

object KelembapanUtils {

    private val weeklyHumidityRanges = listOf(
        WeeklyHumidityRange("MINGGU PERTAMA", 60f, 70f),
        WeeklyHumidityRange("MINGGU KEDUA", 50f, 60f),
        WeeklyHumidityRange("MINGGU KETIGA", 40f, 50f),
        WeeklyHumidityRange("MINGGU KEEMPAT", 40f, 50f),
        WeeklyHumidityRange("MINGGU KELIMA", 30f, 40f),
    )

    fun findWeeklyHumidityRange(keterangan: String): WeeklyHumidityRange? {
        return weeklyHumidityRanges.find { it.week == keterangan }
    }

    fun getKeteranganKelembapan(kelembapan: Float, weeklyHumidityRange: WeeklyHumidityRange): String {
        return when {
            kelembapan > weeklyHumidityRange.maxHumidity -> "Kelembapan Tinggi"
            kelembapan < weeklyHumidityRange.minHumidity -> "Kelembapan Rendah"
            else -> "Kelembapan Normal"
        }
    }
}