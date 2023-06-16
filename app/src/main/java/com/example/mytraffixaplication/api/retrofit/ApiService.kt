package com.example.mytraffixaplication.api.retrofit

import com.example.mytraffixaplication.api.response.TraffixResponse
import com.example.mytraffixaplication.api.retrofit.remote.HomePageResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("api/homepage")
    fun getHomepage(): Call<TraffixResponse>
}