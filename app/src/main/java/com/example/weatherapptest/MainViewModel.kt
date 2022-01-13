package com.example.weatherapptest

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.models.Data
import com.example.weatherapptest.retrofit.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private var liveData = MutableLiveData<Data>()
    private val TAG = "HTTPService"
    private val clientId = "1aaa96c25b6985d3d815358811286f54"

    fun getData(lat: String, lon: String): MutableLiveData<Data> {
        ApiClient.apiService.getData(lat, lon,"metric",clientId).enqueue(object :
            Callback<Data> {
            override fun onResponse(call: Call<Data>, response: Response<Data>) {
                if (response.isSuccessful) {
                    liveData.value = response.body()!!
                }
            }

            override fun onFailure(call: Call<Data>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })
        return liveData
    }
}