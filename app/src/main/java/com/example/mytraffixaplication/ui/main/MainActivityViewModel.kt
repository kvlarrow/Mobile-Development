package com.example.mytraffixaplication.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.mytraffixaplication.api.retrofit.remote.HomePageResponse

class MainActivityViewModel: ViewModel() {
    fun getWeatherInfo(): LiveData<HomePageResponse> {
        return getWeatherInfo()
    }
}