package com.example.weatherapptest

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
    lateinit var binding: ActivityViewBinding
    private val TAG = "ViewActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        )

        window.clearFlags(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)


        val data = intent.getSerializableExtra("data") as AppLatLng
        Log.d(TAG, "onCreate: $data")

        var mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.getData(data.lat, data.lon)
            .observe(this, {
                binding.progress.visibility = View.GONE
                binding.container.alpha = 1f
                Log.d(TAG, "onMarkerDragEnd: $it")
                binding.name.text = it.name
                binding.temp.text = it.main!!.temp.toString()
                Glide.with(this)
                    .load("http://openweathermap.org/img/wn/${it.weather!![0].icon}.png")
                    .into(binding.icon)
                binding.main.text = it.weather!![0].main
                binding.name2.text = "Today:${it.weather!![0].main}"
                binding.temp2.text = "${it.main!!.temp_max}/${it.main!!.temp_min}"
                Glide.with(this)
                    .load("http://openweathermap.org/img/wn/50n.png")
                    .into(binding.windIcon)
                binding.windName2.text = "Wind speed"
                binding.windTemp2.text = "${it.wind!!.speed}km/h"
                Glide.with(this)
                    .load("http://openweathermap.org/img/wn/10n.png")
                    .into(binding.humidityIcon)
                binding.humidityName2.text = "Humidity"
                binding.humidityTemp2.text = "${it.main!!.humidity}%"
            })
    }
}