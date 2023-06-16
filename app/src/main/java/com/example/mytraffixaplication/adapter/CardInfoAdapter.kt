package com.example.mytraffixaplication.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mytraffixaplication.api.retrofit.remote.ListAtcsItem
import com.example.mytraffixaplication.R
import com.example.mytraffixaplication.api.response.Atcs

class CardInfoAdapter(private var listInfo: ArrayList<Atcs>) : RecyclerView.Adapter<CardInfoAdapter.ListViewHolder>() {
    class ListViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvNamaSimpangan: TextView = view.findViewById(R.id.tv_nama_persimpangan)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_atcs, parent, false))

    override fun getItemCount(): Int = listInfo.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.tvNamaSimpangan.text =listInfo[position].info.nama_atcs
    }

    private fun indicatorColor(sum: Int?): Int {
        if (sum != null) {
            return when {
                (sum >= 150) -> {
                    R.color.yellow
                }

                (sum >= 100) -> {
                    R.color.redIndicator
                }

                else -> {
                    R.color.green
                }
            }
        } else {
            throw IllegalArgumentException("Invalid sum value: null")
        }
    }

}