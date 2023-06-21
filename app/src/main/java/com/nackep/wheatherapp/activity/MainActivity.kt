package com.nackep.wheatherapp.activity

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.size
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.nackep.wheatherapp.adapter.VPAdapter
import com.nackep.wheatherapp.api.WeatherAPI
import com.nackep.wheatherapp.databinding.ActivityMainBinding
import com.nackep.wheatherapp.model.WeatherModel
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

var latitude = 0.0
var longitude = 0.0

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var locationManager: LocationManager
    lateinit var locationListener: LocationListener
    lateinit var perLauncher: ActivityResultLauncher<String>
    lateinit var adapter: VPAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = VPAdapter(supportFragmentManager, lifecycle)
        binding.vp2.adapter = adapter
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("TODAY"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("TOMORROW"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("14 DAYS"))



        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    binding.vp2.currentItem = it.position

                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
        binding.vp2.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position))
            }
        })






    }
}