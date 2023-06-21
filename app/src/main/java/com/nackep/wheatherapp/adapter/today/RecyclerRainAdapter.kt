package com.nackep.wheatherapp.adapter.today

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nackep.wheatherapp.databinding.HourRainRowBinding
import com.nackep.wheatherapp.model.Hour

class RecyclerRainAdapter(var hourListForRain: ArrayList<Hour>) :
    RecyclerView.Adapter<RecyclerRainAdapter.RainViewHolder>() {
    class RainViewHolder(val binding: HourRainRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RainViewHolder {
        val binding = HourRainRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RainViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return hourListForRain.size
    }

    override fun onBindViewHolder(holder: RainViewHolder, position: Int) {
        holder.binding.chanceOfRain.text = "${hourListForRain[position].chance_of_rain}%"
        holder.binding.precipAmount.text = hourListForRain[position].precip_mm.toString()
        val hour:String =
            "${hourListForRain[position].time.get(11)}${hourListForRain[position].time.get(12)}${hourListForRain[position].time.get(13)}${hourListForRain[position].time.get(14)}${hourListForRain[position].time.get(15)}"
        holder.binding.rainHour.text = hour
    }
}