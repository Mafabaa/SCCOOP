package com.example.smacc.utility

import com.example.smacc.WeeklyTemperatureRange

object SuhuUtils {

    private val weeklyTemperatureRanges = listOf(
        WeeklyTemperatureRange("MINGGU PERTAMA", 32f, 35f),
        WeeklyTemperatureRange("MINGGU KEDUA", 30f, 32f),
        WeeklyTemperatureRange("MINGGU KETIGA", 28f, 30f),
        WeeklyTemperatureRange("MINGGU KEEMPAT", 25f, 28f),
        WeeklyTemperatureRange("MINGGU KELIMA", 22f, 25f),
    )

    fun findWeeklyTemperatureRange(keterangan: String): WeeklyTemperatureRange? {
        return weeklyTemperatureRanges.find { it.week == keterangan }
    }

    fun getKeteranganSuhu(suhu: Float, weeklyTemperatureRange: WeeklyTemperatureRange): String {
        return when {
            suhu > weeklyTemperatureRange.maxTemperature -> "Suhu Tinggi"
            suhu < weeklyTemperatureRange.minTemperature -> "Suhu Rendah"
            else -> "Suhu Normal"
        }
    }

}