package com.example.smacc

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.content.Context
import android.widget.ImageView
import com.example.smacc.utility.DataSensorKeteranganMonitoring

class AdapterMonitoring(private var sensorDataList: List<DataSensor>) : RecyclerView.Adapter<AdapterMonitoring.SensorViewHolder>() {

    private val iconList = arrayOf(
        R.drawable.thermometer_icon,
        R.drawable.humidity_icon,
        R.drawable.ammonia_icon,
        R.drawable.food_icon,
        R.drawable.water_icon
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SensorViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_monitoring, parent, false)
        return SensorViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SensorViewHolder, position: Int) {
        val dataSensor = sensorDataList[position]

        holder.bindDataSensor(dataSensor)
    }

    override fun getItemCount(): Int {
        return sensorDataList.size
    }

    fun updateData(data: List<DataSensor>) {
        sensorDataList = data
        notifyDataSetChanged()

    }

    inner class SensorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewData: TextView = itemView.findViewById(R.id.txtViewData)
        private val textJudulData: TextView = itemView.findViewById(R.id.textJudulMonitoring)
        private val textStatusData: TextView = itemView.findViewById(R.id.txtStatusMonitoring)
        private val textKeteranganMonitoring: TextView = itemView.findViewById(R.id.txtKeteranganMonitoring)
        private val iconItemData: ImageView = itemView.findViewById(R.id.iconItemDataMonitoring)

        fun bindDataSensor(dataSensor: DataSensor) {
            val sensorText = when (dataSensor) {
                is Suhu -> itemView.context.getString(R.string.suhu_format, dataSensor.suhu)
                is Kelembapan -> itemView.context.getString(R.string.kelembapan_format, dataSensor.kelembapan)
                is Amonia -> itemView.context.getString(R.string.amonia_format, dataSensor.amonia)
                is Distance -> itemView.context.getString(R.string.distance_format, dataSensor.distance)
                is Waterlevel -> itemView.context.getString(R.string.waterlevel_format, dataSensor.waterelvel)
                else -> ""
            }

            textViewData.text = sensorText

            val judulDataMonitoring = getTxtJudulMonitoring(itemView.context, dataSensor.dataType)
            textJudulData.text = judulDataMonitoring

            val statusDataMonitoring = getTxtStatusMonitoring(itemView.context, dataSensor.dataType)
            textStatusData.text = statusDataMonitoring

            val iconPosition = adapterPosition % iconList.size
            val iconDrawable = iconList[iconPosition]
            iconItemData.setImageResource(iconDrawable)

            if (dataSensor is Distance) {
                // Menampilkan keterangan makanan berdasarkan nilai distance
                textKeteranganMonitoring.text = when (dataSensor.distance) {
                    5 -> "Makanan Penuh"
                    4 -> "Makanan Penuh"
                    3 -> "Makanan Hampir Habis"
                    2 -> "Makanan Hampir Habis"
                    1 -> "Makanan Habis"
                    else -> "Status Makanan Tidak Diketahui"
                }
            } else if (dataSensor is Waterlevel) {
                // Menampilkan keternangan minum berdasarkan nilai waterlevel
                textKeteranganMonitoring.text = when (dataSensor.waterelvel) {
                    4 -> "Minum Penuh"
                    3 -> "Minum Penuh"
                    2 -> "Minum Hampir Habis"
                    1 -> "Minum Hampir Habis"
                    0 -> "Minum Habis"
                    else -> "Status Minum Tidak Diketahui"
                }
            } else {
                // Menampilkan keterangan untuk data selain distance
                val dataSensorKeteranganMonitoring = DataSensorKeteranganMonitoring()
                val keterangan = dataSensorKeteranganMonitoring.getKeteranganMonitoring(dataSensor)

                // Set teks keterangan
                textKeteranganMonitoring.text = keterangan
            }
        }
    }

    // Mendapatkan Text Judul Monitoring dari String untuk ditampilkan di setiap cardview
    fun getTxtJudulMonitoring(context: Context,dataType: String): String {
        return when (dataType) {
            "suhu" -> context.getString(R.string.text_suhu)
            "kelembapan" -> context.getString(R.string.text_kelembapan)
            "amonia" -> context.getString(R.string.text_amonia)
            "distance" -> context.getString(R.string.text_distance)
            "waterlevel" -> context.getString(R.string.text_waterlevel)
            else -> context.getString(R.string.text_suhu)
        }
    }

    // Mendapatkan Text Status Monitoring dari String untuk ditampilkan di setiap cardview
    fun getTxtStatusMonitoring(context: Context,dataType: String): String {
        return when (dataType) {
            "suhu" -> context.getString(R.string.txt_status_suhu)
            "kelembapan" -> context.getString(R.string.txt_status_kelembapan)
            "amonia" -> context.getString(R.string.txt_status_amonia)
            "distance" -> context.getString(R.string.txt_status_distance)
            "waterlevel" -> context.getString(R.string.txt_status_waterlevel)
            else -> context.getString(R.string.text_suhu)
        }
    }
}