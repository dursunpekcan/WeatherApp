package com.nackep.wheatherapp.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.nackep.wheatherapp.activity.latitude
import com.nackep.wheatherapp.activity.longitude
import com.nackep.wheatherapp.adapter.today.HourRowAdapter
import com.nackep.wheatherapp.adapter.today.RecyclerRainAdapter
import com.nackep.wheatherapp.adapter.today.WindAdapter
import com.nackep.wheatherapp.api.WeatherAPI
import com.nackep.wheatherapp.databinding.FragmentTodayBinding
import com.nackep.wheatherapp.model.Hour
import com.nackep.wheatherapp.model.WeatherModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


class TodayFragment : Fragment() {
    private var _binding: FragmentTodayBinding? = null
    private val binding get() = _binding!!

    lateinit var locationManager: LocationManager
    lateinit var locationListener: LocationListener
    lateinit var perLauncher: ActivityResultLauncher<String>
    lateinit var hourAdapter: HourRowAdapter
    lateinit var windAdapter: WindAdapter
    lateinit var hourRainAdapter: RecyclerRainAdapter
    lateinit var hourList: ArrayList<Hour>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTodayBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerLauncher()

        locationManager =
            requireActivity().getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager







        if (ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                Snackbar.make(binding.root, "Permission Needed for", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Give Permission") {
                        perLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                    }.show()

            } else {
                perLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }

        } else {
            locationListener = object : LocationListener {
                override fun onLocationChanged(location: Location) {
                    latitude = location.latitude
                    longitude = location.longitude
                    loadData()
                }
            }
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 3600000, 0f, locationListener
            )
        }


    }

    fun setRecyclerViews() {
        hourAdapter = HourRowAdapter(hourList, requireContext())
        binding.recyclerviewHour.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerviewHour.adapter = hourAdapter

        hourRainAdapter = RecyclerRainAdapter(hourList)
        binding.recyclerViewRain.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewRain.adapter = hourRainAdapter

        windAdapter = WindAdapter(hourList)
        binding.windRecyclerView.adapter = windAdapter
        binding.windRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)


    }

    fun registerLauncher() {
        perLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
                if (result) {
                    if (ContextCompat.checkSelfPermission(
                            requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER, 3600000, 0f, locationListener
                        )
                    }
                } else {
                    Toast.makeText(requireContext(), "Permission Needed", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun setCurrentWindDirection(direction: String) {
        if (direction == "N") {
            binding.currentWindImage.rotation = 0f
        }
        if (direction == "NNE") {
            binding.currentWindImage.rotation = 22f
        }
        if (direction == "NE") {
            binding.currentWindImage.rotation = 45F
        }
        if (direction == "ENE") {
            binding.currentWindImage.rotation = 67f
        }
        if (direction == "E") {
            binding.currentWindImage.rotation = 90f
        }
        if (direction == "ESE") {
            binding.currentWindImage.rotation = 112f
        }
        if (direction == "SE") {
            binding.currentWindImage.rotation = 135f
        }
        if (direction == "SSE") {
            binding.currentWindImage.rotation = 157f
        }
        if (direction == "S") {
            binding.currentWindImage.rotation = 180f
        }
        if (direction == "SSW") {
            binding.currentWindImage.rotation = 202f
        }
        if (direction == "SW") {
            binding.currentWindImage.rotation = 225f
        }
        if (direction == "WSW") {
            binding.currentWindImage.rotation = 247f
        }
        if (direction == "W") {
            binding.currentWindImage.rotation = 270f
        }
        if (direction == "WNW") {
            binding.currentWindImage.rotation = 292f
        }
        if (direction == "NW") {
            binding.currentWindImage.rotation = 315f
        }
        if (direction == "NNW") {
            binding.currentWindImage.rotation = 337f
        }

    }

    fun loadData() {
        val retrofit = Retrofit.Builder().baseUrl("BASE_URL")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val service = retrofit.create(WeatherAPI::class.java)
        val call = service.getData()


        call.enqueue(object : Callback<WeatherModel> {
            override fun onResponse(call: Call<WeatherModel>, response: Response<WeatherModel>) {
                response.body()?.let {


                    try {
                        hourList = ArrayList()
                        Glide.with(requireContext()).load("https:" + it.current.condition.icon)
                            .into(binding.imageView2)
                        binding.tvPlaceName.text = "${it.location.name}"
                        binding.tvCurrentTemp.text = "${it.current.temp_c.toInt()}°C"
                        it.forecast.forecastday[0].hour

                        for (hour in it.forecast.forecastday[0].hour) {
                            hourList.add(hour)
                        }

                        binding.tvHumidity.text = "${it.current.humidity}%"
                        binding.tvPressure.text = "${it.current.pressure_mb.toInt()} mBar"
                        binding.tvUvIndex.text = "${it.current.uv.toInt()}"

                        var currentHour =
                            "${it.location.localtime.subSequence(11,13)}"
                        if (it.location.localtime.subSequence(11,13).contains(":")){
                            currentHour="${it.location.localtime.get(11)}0"
                        }
                        println(currentHour)
                        for (hourIndex in hourList.indices) {
                            if (hourIndex == currentHour.toInt() - 1) {
                                binding.tvRain.text =
                                    "${it.forecast.forecastday[0].hour[hourIndex].chance_of_rain}%"
                            }
                        }
                        setRecyclerViews()

                        setCurrentWindDirection(it.current.wind_dir)
                        binding.tvCurrentWindSpeed.text = "${it.current.wind_kph.toInt()} kph"
                        binding.tvSunrise.text = it.forecast.forecastday.get(0).astro.sunrise
                        binding.tvSunset.text = it.forecast.forecastday.get(0).astro.sunset
                        binding.progressBar.visibility=View.GONE
                        binding.mainScrollView.visibility=View.VISIBLE


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




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}