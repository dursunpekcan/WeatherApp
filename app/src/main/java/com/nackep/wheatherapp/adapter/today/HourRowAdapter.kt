package com.nackep.wheatherapp.adapter.today

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nackep.wheatherapp.databinding.HourRowBinding
import com.nackep.wheatherapp.model.Hour

class HourRowAdapter(var hourList: ArrayList<Hour>, val context: Context) :
    RecyclerView.Adapter<HourRowAdapter.HourRowHolder>() {
    class HourRowHolder(var binding: HourRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourRowHolder {
        val binding = HourRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HourRowHolder(binding)
    }

    override fun getItemCount(): Int {
        return hourList.size
    }

    override fun onBindViewHolder(holder: HourRowHolder, position: Int) {
        holder.binding.tvHourTemp.text = hourList[position].temp_c.toInt().toString() +"°C"
        Glide.with(context).load("https:" + hourList.get(position).condition.icon).fitCenter()
            .into(holder.binding.hourWeatherImg)
        holder.binding.tvHour.text =
            hourList.get(position).time.get(11).toString()+
            hourList.get(position).time.get(12).toString()+
            hourList.get(position).time.get(13).toString()+
            hourList.get(position).time.get(14).toString()+
            hourList.get(position).time.get(15).toString()

    }
}