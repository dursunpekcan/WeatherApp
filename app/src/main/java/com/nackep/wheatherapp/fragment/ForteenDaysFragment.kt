package com.nackep.wheatherapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.nackep.wheatherapp.R
import com.nackep.wheatherapp.adapter.ForteenDaysAdapter
import com.nackep.wheatherapp.api.WeatherAPI
import com.nackep.wheatherapp.databinding.FragmentForteenDaysBinding
import com.nackep.wheatherapp.databinding.FragmentTodayBinding
import com.nackep.wheatherapp.model.Forecast
import com.nackep.wheatherapp.model.Forecastday
import com.nackep.wheatherapp.model.WeatherModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.sql.Time
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date


class ForteenDaysFragment : Fragment() {
    private var _binding: FragmentForteenDaysBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ForteenDaysAdapter
    private lateinit var dayList: ArrayList<Forecastday>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForteenDaysBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()


    }

    fun setRecyclerView(){
        adapter = ForteenDaysAdapter(dayList,requireContext())
        binding.forteendaysRecyclerView.adapter = adapter
        binding.forteendaysRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    fun loadData() {
        val retrofit = Retrofit.Builder().baseUrl("https://api.weatherapi.com/v1/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val service = retrofit.create(WeatherAPI::class.java)
        val call = service.getDataByDays(days = "14")


        call.enqueue(object : Callback<WeatherModel> {
            override fun onResponse(call: Call<WeatherModel>, response: Response<WeatherModel>) {
                response.body()?.let { weathermodel ->
                    dayList=ArrayList()
                    for (element in weathermodel.forecast.forecastday){
                        dayList.add(element)
                    }
                    setRecyclerView()
                }
            }

            override fun onFailure(call: Call<WeatherModel>, t: Throwable) {

            }

        })


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}