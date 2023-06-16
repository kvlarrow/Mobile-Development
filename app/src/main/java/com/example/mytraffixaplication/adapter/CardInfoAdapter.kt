package com.example.mytraffixaplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mytraffixaplication.R
import com.example.mytraffixaplication.api.response.Atcs
import com.example.mytraffixaplication.helper.calculateElapsedTime

class CardInfoAdapter(private var listInfo: ArrayList<Atcs>) : RecyclerView.Adapter<CardInfoAdapter.ListViewHolder>() {
    class ListViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvNamaSimpangan: TextView = view.findViewById(R.id.tv_nama_persimpangan)
        val tvJumlahKendaraan1: TextView = view.findViewById(R.id.tv_jumlah_kendaraan_1)
        val tvJumlahKendaraan2: TextView = view.findViewById(R.id.tv_jumlah_kendaraan_2)
        val tvJumlahKendaraan3: TextView = view.findViewById(R.id.tv_jumlah_kendaraan_3)
        val indicator1: CardView = view.findViewById(R.id.indicator_1)
        val indicator2: CardView = view.findViewById(R.id.indicator_2)
        val indicator3: CardView = view.findViewById(R.id.indicator_3)
        val dataSince1: TextView = view.findViewById(R.id.add_since_1)
        val dataSince2: TextView = view.findViewById(R.id.add_since_2)
        val dataSince3: TextView = view.findViewById(R.id.add_since_3)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_atcs, parent, false))

    override fun getItemCount(): Int = listInfo.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listInfo[position]

        val car1 = data.statistik[0].car
        val motorcycle1 = data.statistik[0].motorcycle
        val bus1 = data.statistik[0].bus
        val truck1 = data.statistik[0].truck

        val car2 = data.statistik[1].car
        val motorcycle2 = data.statistik[1].motorcycle
        val bus2 = data.statistik[1].bus
        val truck2 = data.statistik[1].truck

        val car3 = data.statistik[2].car
        val motorcycle3 = data.statistik[2].motorcycle
        val bus3 = data.statistik[2].bus
        val truck3 = data.statistik[2].truck

        val timeAdd1 = data.statistik[0].createdAt
        val timeAdd2 = data.statistik[1].createdAt
        val timeAdd3 = data.statistik[2].createdAt

        val timeNow1 = calculateElapsedTime(timeAdd1)
        val timeNow2 = calculateElapsedTime(timeAdd2)
        val timeNow3 = calculateElapsedTime(timeAdd3)

        val kepadatan1 = kepadatanCount(car1, motorcycle1, bus1, truck1) ?: 0
        val kepadatan2 = kepadatanCount(car2, motorcycle2, bus2, truck2) ?: 0
        val kepadatan3 = kepadatanCount(car3, motorcycle3, bus3, truck3) ?: 0

        val colorResId1 = indicatorColor(kepadatan1)
        val colorResId2 = indicatorColor(kepadatan2)
        val colorResId3 = indicatorColor(kepadatan3)

        //Nama Jalan
        holder.tvNamaSimpangan.text = data.info.nama_atcs

        //Kepadatan Kendaraan
        holder.tvJumlahKendaraan1.text = "$kepadatan1 Kendaraan"
        holder.tvJumlahKendaraan2.text = "$kepadatan2 Kendaraan"
        holder.tvJumlahKendaraan3.text = "$kepadatan3 Kendaraan"

        //Indikator Warna
        holder.indicator1.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, colorResId1))
        holder.indicator2.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, colorResId2))
        holder.indicator3.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, colorResId3))

        holder.dataSince1.text = "Update $timeNow1 menit yang lalu"
        holder.dataSince2.text = "Update $timeNow2 menit yang lalu"
        holder.dataSince3.text = "Update $timeNow3 menit yang lalu"

    }

    private fun kepadatanCount(car: Int?, motorcycle: Int?, bus: Int?, truck: Int?): Int{
        return listOfNotNull(car, motorcycle, bus, truck).sum()
    }

    private fun indicatorColor(sum: Int?): Int {
        return when (sum) {
            in 0..10 -> R.color.green // warna untuk jumlah rendah
            in 11..50 -> R.color.yellow // warna untuk jumlah sedang
            else -> R.color.redIndicator // warna untuk jumlah tinggi
        }
    }

}