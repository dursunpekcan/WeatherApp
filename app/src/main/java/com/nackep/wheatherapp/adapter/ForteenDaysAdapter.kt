package com.nackep.wheatherapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nackep.wheatherapp.databinding.ForteenDaysRowBinding
import com.nackep.wheatherapp.model.Forecastday
import com.nackep.wheatherapp.model.Hour
import java.nio.file.attribute.DosFileAttributeView
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.Instant
import java.util.Calendar
import java.util.Date
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.parseIsoString

class ForteenDaysAdapter(var dayList: ArrayList<Forecastday>, var context: Context) :
    RecyclerView.Adapter<ForteenDaysAdapter.ForteenDaysViewHolder>() {
    class ForteenDaysViewHolder(var binding: ForteenDaysRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForteenDaysViewHolder {
        return ForteenDaysViewHolder(
            ForteenDaysRowBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return dayList.size
    }


    override fun onBindViewHolder(holder: ForteenDaysViewHolder, position: Int) {
        //tvDay
        val calendar = Calendar.getInstance()
        calendar.set(
            Calendar.DAY_OF_YEAR, Calendar.getInstance().get(Calendar.DAY_OF_YEAR) + position
        )
        holder.binding.tvDay.text =
            "${DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.time)}"

        if (position == 0) {
            holder.binding.tvDay.text = "Today"
        }
        //dayImage
        Glide.with(context).load("https:${dayList[position].day.condition.icon}")
            .into(holder.binding.dayWheatherImg)

        //Average Temprature
        var sunrise = dayList.get(position).astro.sunrise
        if (sunrise.contains("AM")) {
            if (sunrise.get(0) == '0') {
                sunrise = sunrise.get(1).toString()
            } else {
                sunrise = sunrise.subSequence(0, 2).toString()
            }
        } else {
            if (sunrise.get(0) == '0') {
                sunrise = sunrise.get(1).toString()
            } else {
                sunrise = sunrise.subSequence(0, 2).toString()
            }
            sunrise = "${sunrise.toInt() + 12}"
        }

        var sunset = dayList.get(position).astro.sunset
        if (sunset.contains("AM")) {
            if (sunset.get(0) == '0') {
                sunset = sunset.get(1).toString()
            } else {
                sunset = sunset.subSequence(0, 2).toString()
            }
        } else {
            if (sunset.get(0) == '0') {
                sunset = sunset.get(1).toString()
            } else {
                sunset = sunset.subSequence(0, 2).toString()
            }
            sunset = "${sunset.toInt() + 12}"
        }

        var nextDaySunrise = ""
        var nextDaySunset = ""
        if (position != 13) {
            nextDaySunrise = dayList.get(position + 1).astro.sunrise
            if (nextDaySunrise.contains("AM")) {
                if (nextDaySunrise.get(0) == '0') {
                    nextDaySunrise = nextDaySunrise.get(1).toString()
                } else {
                    nextDaySunrise = nextDaySunrise.subSequence(0, 2).toString()
                }
            } else {
                if (nextDaySunrise.get(0) == '0') {
                    nextDaySunrise = nextDaySunrise.get(1).toString()
                } else {
                    nextDaySunrise = nextDaySunrise.subSequence(0, 2).toString()
                }
                nextDaySunrise = "${nextDaySunrise.toInt() + 12}"
            }

            nextDaySunset = dayList.get(position + 1).astro.sunset
            if (nextDaySunset.contains("AM")) {
                if (nextDaySunset.get(0) == '0') {
                    nextDaySunset = nextDaySunset.get(1).toString()
                } else {
                    nextDaySunset = nextDaySunset.subSequence(0, 2).toString()
                }
            } else {
                if (nextDaySunset.get(0) == '0') {
                    nextDaySunset = nextDaySunset.get(1).toString()
                } else {
                    nextDaySunset = nextDaySunset.subSequence(0, 2).toString()
                }
                nextDaySunset = "${nextDaySunset.toInt() + 12}"
            }
        }

        var totalMorningTemp = 0.0
        var countMorningHours = 0

        var totalNightTemp = 0.0
        var countNightHours = 0
        var totalNextDayNightTemp = 0.0
        var countNextDayNightHours = 0
        for (i in dayList.get(position).hour.indices) {
            if (dayList.get(position).hour.get(i).is_day == 1 && i >= sunrise.toInt() && i <= sunset.toInt()) {
                countMorningHours++
                totalMorningTemp += dayList.get(position).hour.get(i).temp_c
            }
            if (dayList.get(position).hour.get(i).is_day == 0 && i >= sunset.toInt()) {
                countNightHours++
                totalNightTemp += dayList.get(position).hour.get(i).temp_c
            }
            if (position != 13) {
                if (dayList.get(position + 1).hour.get(i).is_day == 0 && i <= nextDaySunrise.toInt()) {
                    countNextDayNightHours++
                    totalNextDayNightTemp += dayList.get(position + 1).hour.get(i).temp_c
                }
            }
        }
        holder.binding.tvMorning.text = "${(totalMorningTemp / countMorningHours).toInt()}°"
        holder.binding.tvNight.text = "${((totalNightTemp + totalNextDayNightTemp) / (countNightHours + countNextDayNightHours)).toInt()}°"




    }


}