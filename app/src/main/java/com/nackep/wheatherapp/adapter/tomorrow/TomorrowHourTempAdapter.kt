package com.nackep.wheatherapp.adapter.tomorrow

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nackep.wheatherapp.databinding.HourRowBinding
import com.nackep.wheatherapp.model.Hour

class TomorrowHourTempAdapter(var hourList: ArrayList<Hour>, val context: Context):RecyclerView.Adapter<TomorrowHourTempAdapter.ToomorrowHourTempViewHolder>() {
    class ToomorrowHourTempViewHolder(var binding:HourRowBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToomorrowHourTempViewHolder {
        val binding = HourRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ToomorrowHourTempViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return hourList.size
    }

    override fun onBindViewHolder(holder: ToomorrowHourTempViewHolder, position: Int) {
        holder.binding.tvHourTemp.text = hourList[position].temp_c.toInt().toString() +"°C"
        Glide.with(context).load("https:" + hourList.get(position).condition.icon).fitCenter()
            .into(holder.binding.hourWeatherImg)
        holder.binding.tvHour.text = hourList.get(position).time.subSequence(11,16)


    }

}