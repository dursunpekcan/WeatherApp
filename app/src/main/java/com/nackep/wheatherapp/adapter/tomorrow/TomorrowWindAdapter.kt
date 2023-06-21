package com.nackep.wheatherapp.adapter.tomorrow

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.nackep.wheatherapp.databinding.RowWindBinding
import com.nackep.wheatherapp.model.Hour

class TomorrowWindAdapter(var hourList: ArrayList<Hour>):RecyclerView.Adapter<TomorrowWindAdapter.TomorrowWindHolder>() {
    class TomorrowWindHolder(var binding: RowWindBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TomorrowWindHolder {
        return TomorrowWindAdapter.TomorrowWindHolder(
            RowWindBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return hourList.size
    }

    override fun onBindViewHolder(holder: TomorrowWindHolder, position: Int) {
        setWindImgDirection(hourList.get(position).wind_dir,holder.binding.windDirectionImg)
        holder.binding.windSpeed.text="${hourList.get(position).wind_kph.toInt()}kph"
        holder.binding.windHour.text="${hourList.get(position).time.subSequence(11,16)}"
        println(hourList.get(position).time)
    }
    fun setWindImgDirection(direction:String,imageView: ImageView) {
        if (direction == "N") {
            imageView.rotation = 0f
        }
        if (direction == "NNE") {
            imageView.rotation = 22f
        }
        if (direction == "NE") {
            imageView.rotation = 45F
        }
        if (direction == "ENE") {
            imageView.rotation = 67f
        }
        if (direction == "E") {
            imageView.rotation = 90f
        }
        if (direction == "ESE") {
            imageView.rotation = 112f
        }
        if (direction == "SE") {
            imageView.rotation= 135f
        }
        if (direction == "SSE") {
            imageView.rotation = 157f
        }
        if (direction == "S") {
            imageView.rotation = 180f
        }
        if (direction == "SSW") {
            imageView.rotation = 202f
        }
        if (direction == "SW") {
            imageView.rotation = 225f
        }
        if (direction == "WSW") {
            imageView.rotation = 247f
        }
        if (direction == "W") {
            imageView.rotation = 270f
        }
        if (direction == "WNW") {
            imageView.rotation = 292f
        }
        if (direction == "NW") {
            imageView.rotation = 315f
        }
        if (direction == "NNW") {
            imageView.rotation = 337f
        }

    }
}