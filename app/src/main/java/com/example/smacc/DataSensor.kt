package com.example.smacc

abstract class DataSensor {
    abstract val dataType: String
    abstract val keterangan: String
}

class Suhu(val suhu: Float, override val keterangan: String) : DataSensor() {
    override val dataType: String
        get() = "suhu"
}

class Kelembapan(val kelembapan: Float, override val keterangan: String) : DataSensor() {
    override val dataType: String
        get() = "kelembapan"
}

class Amonia(val amonia: Float, override val keterangan: String) : DataSensor() {
    override val dataType: String
        get() = "amonia"
}

class Distance(val distance: Int, override val keterangan: String): DataSensor() {
    override val dataType: String
        get() = "distance"
}

class Waterlevel(val waterelvel: Int, override val keterangan: String): DataSensor() {
    override val dataType: String
        get() = "waterlevel"
}

data class WeeklyTemperatureRange(val week: String, val minTemperature: Float, val maxTemperature: Float)

data class WeeklyHumidityRange(val week: String, val minHumidity: Float, val maxHumidity: Float)

data class WeeklyAmoniaRange(val week: String, val maxAmonia: Float)