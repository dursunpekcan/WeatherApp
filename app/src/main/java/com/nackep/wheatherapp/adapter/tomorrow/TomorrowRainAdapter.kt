package com.nackep.wheatherapp.adapter.tomorrow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.nackep.wheatherapp.adapter.today.RecyclerRainAdapter
import com.nackep.wheatherapp.databinding.HourRainRowBinding
import com.nackep.wheatherapp.model.Hour

class TomorrowRainAdapter(var hourListForRain: ArrayList<Hour>):RecyclerView.Adapter<TomorrowRainAdapter.TomorrowRainHolder>() {
    class TomorrowRainHolder(var binding:HourRainRowBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TomorrowRainHolder {
        val binding = HourRainRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TomorrowRainHolder(binding)
    }

    override fun getItemCount(): Int {
        return hourListForRain.size
    }

    override fun onBindViewHolder(holder: TomorrowRainHolder, position: Int) {
        holder.binding.chanceOfRain.text = "${hourListForRain[position].chance_of_rain}%"
        holder.binding.precipAmount.text = hourListForRain[position].precip_mm.toString()
        val hour =
            "${hourListForRain[position].time.subSequence(11,16)}"
        holder.binding.rainHour.text = hour
    }
}