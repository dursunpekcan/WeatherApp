package com.nackep.wheatherapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.nackep.wheatherapp.R
import com.nackep.wheatherapp.adapter.tomorrow.TomorrowHourTempAdapter
import com.nackep.wheatherapp.adapter.tomorrow.TomorrowRainAdapter
import com.nackep.wheatherapp.adapter.tomorrow.TomorrowWindAdapter
import com.nackep.wheatherapp.api.WeatherAPI
import com.nackep.wheatherapp.databinding.FragmentTodayBinding
import com.nackep.wheatherapp.databinding.FragmentTomorrowBinding
import com.nackep.wheatherapp.model.Hour
import com.nackep.wheatherapp.model.WeatherModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


class TomorrowFragment : Fragment() {
    private var _binding: FragmentTomorrowBinding? = null
    private val binding get() = _binding!!
    lateinit var tomorrowHourTempAdapter: TomorrowHourTempAdapter
    lateinit var tomorrowRainAdapter: TomorrowRainAdapter
    lateinit var tomorrowWindAdapter: TomorrowWindAdapter
    lateinit var hourList: ArrayList<Hour>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTomorrowBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()

    }

    fun setRecyclerViews() {
        tomorrowHourTempAdapter = TomorrowHourTempAdapter(hourList, requireContext())
        binding.rycTomorrowTempByHours.adapter = tomorrowHourTempAdapter

        tomorrowRainAdapter = TomorrowRainAdapter(hourList)
        binding.rycTomorrowRain.adapter = tomorrowRainAdapter

        tomorrowWindAdapter = TomorrowWindAdapter(hourList)
        binding.rycTomorrowWind.adapter = tomorrowWindAdapter
    }

    fun loadData() {
        val retrofit = Retrofit.Builder().baseUrl("https://api.weatherapi.com/v1/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val service = retrofit.create(WeatherAPI::class.java)
        val call = service.getDataByDays(days = "14")


        call.enqueue(object : Callback<WeatherModel> {
            override fun onResponse(call: Call<WeatherModel>, response: Response<WeatherModel>) {
                response.body()?.let {


                    try {

                        println(it.forecast.forecastday.get(1).day.condition.icon)
                        setTomorrowImage(
                            it.forecast.forecastday.get(1).day.condition.icon,
                            binding.imageView
                        )
                        binding.tvTomorrowHumidity.text =
                            "${it.forecast.forecastday.get(1).day.avghumidity.toInt()}%"
                        binding.tvDayTemp.text = "Day ${getMorningAvgTemp(it)}°"
                        binding.tvNightTemp.text = "Night ${getNightAvgTemp(it)}°"
                        hourList = ArrayList()
                        for (hour in it.forecast.forecastday.get(1).hour) {
                            hourList.add(hour)
                        }
                        var hourListSortedByWindKph=hourList.sortedBy {
                            it.wind_kph
                        }
                        binding.tvTomorrowWindRange.text="${hourListSortedByWindKph.get(0).wind_kph.toInt()}-${hourListSortedByWindKph.get(hourListSortedByWindKph.size-1).wind_kph.toInt()} kph"
                        setRecyclerViews()
                        binding.mainScrollView.visibility=View.VISIBLE
                        binding.tomorrowProggressBar.visibility=View.GONE
                    } catch (e: IOException) {
                        println(e.localizedMessage)
                    }

                }
            }

            override fun onFailure(call: Call<WeatherModel>, t: Throwable) {
                println(t.localizedMessage)
            }

        })

    }

    fun setTomorrowImage(url: String, imageView: ImageView) {
        Glide.with(requireContext()).load("https:$url").into(imageView)
    }

    fun getMorningAvgTemp(weather: WeatherModel): Int {
        var sunrise = weather.forecast.forecastday.get(1).astro.sunrise
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

        var sunset = weather.forecast.forecastday.get(1).astro.sunset
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


        var totalMorningAvg = 0
        var countMorningHours = 0
        for (i in sunrise.toInt()..sunset.toInt()) {
            totalMorningAvg += weather.forecast.forecastday.get(1).hour.get(i).temp_c.toInt()
            countMorningHours++
        }

        return totalMorningAvg / countMorningHours
    }

    fun getNightAvgTemp(weather: WeatherModel): Int {
        var sunrise = weather.forecast.forecastday.get(1).astro.sunrise
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

        var sunset = weather.forecast.forecastday.get(1).astro.sunset
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

        nextDaySunrise = weather.forecast.forecastday.get(2).astro.sunrise
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

        nextDaySunset = weather.forecast.forecastday.get(2).astro.sunset
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

        var countNightHours = 0
        var totalNightTemp = 0.0
        var countNextDayNightHours = 0
        var totalNextDayNightTemp = 0.0

        for (i in weather.forecast.forecastday.get(1).hour.indices) {
            if (weather.forecast.forecastday.get(1).hour.get(i).is_day == 0 && i >= sunset.toInt()) {
                countNightHours++
                totalNightTemp += weather.forecast.forecastday.get(1).hour.get(i).temp_c
            }
            if (weather.forecast.forecastday.get(2).hour.get(i).is_day == 0 && i <= nextDaySunrise.toInt()) {
                countNextDayNightHours++
                totalNextDayNightTemp += weather.forecast.forecastday.get(2).hour.get(i).temp_c
            }
        }
        return ((totalNightTemp + totalNextDayNightTemp) / (countNightHours + countNextDayNightHours)).toInt()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}