package com.example.smacc.utility

import com.example.smacc.WeeklyAmoniaRange

object AmoniaUtils {

    private val weeklyAmoniaRanges = listOf(
        WeeklyAmoniaRange("MINGGU PERTAMA", 10f),
        WeeklyAmoniaRange("MINGGU KEDUA", 10f),
        WeeklyAmoniaRange("MINGGU KETIGA", 10f),
        WeeklyAmoniaRange("MINGGU KEEMPAT", 10f),
        WeeklyAmoniaRange("MINGGU KELIMA", 10f)
    )

    fun findWeeklyAmoniaRange(keterangan: String): WeeklyAmoniaRange? {
        return weeklyAmoniaRanges.find { it.week == keterangan }
    }

    fun getKeteranganAmonia(amonia: Float, weeklyAmoniaRange: WeeklyAmoniaRange): String {
        return when {
            amonia > weeklyAmoniaRange.maxAmonia -> "Amonia Tinggi"
            else -> "Amonia Normal"
        }
    }

}