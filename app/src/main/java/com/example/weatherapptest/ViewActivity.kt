package com.example.weatherapptest

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.weatherapp.models.AppLatLng
import com.example.weatherapptest.databinding.ActivityViewBinding

class ViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewBinding
    lateinit var list: HashMap<String, String>
    private val TAG = "ViewActivity"

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.clearFlags(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        }

        list = HashMap()
        list.put(
            "Sunny",
            "https://images.unsplash.com/photo-1541119638723-c51cbe2262aa?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=873&q=80"
        )
        list.put(
            "Cloudy sun",
            "https://images.unsplash.com/photo-1419833173245-f59e1b93f9ee?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80"
        )
        list.put(
            "Cloudy",
            "https://images.unsplash.com/photo-1617072635572-3d081e84ddbf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=387&q=80"
        )
        list.put(
            "Rain",
            "https://images.unsplash.com/photo-1534274988757-a28bf1a57c17?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=435&q=80"
        )
        list.put(
            "thunder",
            "https://images.unsplash.com/photo-1599070221195-bf2801877d7e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80"
        )
        list.put(
            "Snowy",
            "https://images.unsplash.com/photo-1544235653-a313b8a430d9?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=464&q=80"
        )

        val data = intent.getSerializableExtra("data") as AppLatLng
        Log.d(TAG, "onCreate: $data")

        val mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.getData(data.lat, data.lon)
            .observe(this, {
                when (it.weather!![0].icon) {
                    "11d", "11n" -> {
                        Glide.with(this)
                            .load(list["thunder"])
                            .into(binding.imageView)
                    }
                    "01d", "01n" -> {
                        Glide.with(this)
                            .load(list["Sunny"])
                            .into(binding.imageView)
                    }
                    "02d", "02n" -> {
                        Glide.with(this)
                            .load(list["Cloudy sun"])
                            .into(binding.imageView)
                    }
                    "04d", "04n" -> {
                        Glide.with(this)
                            .load(list["Cloudy"])
                            .into(binding.imageView)
                    }
                    "09d", "09n", "10d", "10n" -> {
                        Glide.with(this)
                            .load(list["Rain"])
                            .into(binding.imageView)
                    }
                    "13d", "13n" -> {
                        Glide.with(this)
                            .load(list["Snowy"])
                            .into(binding.imageView)
                    }
                    "50d", "50n" -> {
                        Glide.with(this)
                            .load(list["Cloudy"])
                            .into(binding.imageView)
                    }

                }
                Log.d(TAG, "onMarkerDragEnd: $it")
                binding.name.text = it.name
                binding.temp.text = it.main!!.temp.toString()
                Glide.with(this)
                    .load("http://openweathermap.org/img/wn/${it.weather!![0].icon}.png")
                    .into(binding.icon)
                binding.main.text = it.weather!![0].main
                binding.name2.text = "Today:${it.weather!![0].main}"
                binding.temp2.text = "${it.main!!.temp_max}/${it.main!!.temp_min}"
                Glide.with(this).load("http://openweathermap.org/img/wn/50n.png")
                    .into(binding.windIcon)
                binding.windName2.text = "Wind speed"
                binding.windTemp2.text = "${it.wind!!.speed}km/h"
                Glide.with(this).load("http://openweathermap.org/img/wn/10n.png")
                    .into(binding.humidityIcon)
                binding.humidityName2.text = "Humidity"
                binding.humidityTemp2.text = "${it.main!!.humidity}%"
                binding.progress.visibility = View.GONE
                binding.container.setAlpha(1.0F)
            })
    }
}