package com.example.smacc.utility

import com.example.smacc.*
import com.example.smacc.utility.AmoniaUtils.findWeeklyAmoniaRange
import com.example.smacc.utility.AmoniaUtils.getKeteranganAmonia
import com.example.smacc.utility.KelembapanUtils.findWeeklyHumidityRange
import com.example.smacc.utility.KelembapanUtils.getKeteranganKelembapan
import com.example.smacc.utility.SuhuUtils.findWeeklyTemperatureRange
import com.example.smacc.utility.SuhuUtils.getKeteranganSuhu

class DataSensorKeteranganMonitoring {
    fun getKeteranganMonitoring(dataSensor: DataSensor): String {
        return when (dataSensor) {
            is Suhu -> {
                val weeklyTemperatureRange = findWeeklyTemperatureRange(dataSensor.keterangan)
                if (weeklyTemperatureRange != null) {
                    getKeteranganSuhu(dataSensor.suhu, weeklyTemperatureRange)
                } else {
                    ""
                }
            }
            is Kelembapan -> {
                val weeklyHumidityRange = findWeeklyHumidityRange(dataSensor.keterangan)
                if (weeklyHumidityRange != null) {
                    getKeteranganKelembapan(dataSensor.kelembapan, weeklyHumidityRange)
                } else {
                    ""
                }
            }
            is Amonia -> {
                val weeklyAmoniaRange = findWeeklyAmoniaRange(dataSensor.keterangan)
                if (weeklyAmoniaRange != null) {
                    getKeteranganAmonia(dataSensor.amonia, weeklyAmoniaRange)
                } else {
                    ""
                }
            }
            else -> ""
        }
    }
}