package com.example.mytraffixaplication.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.mytraffixaplication.adapter.CardInfoAdapter
import com.example.mytraffixaplication.api.response.Atcs
import com.example.mytraffixaplication.api.response.TraffixResponse
import com.example.mytraffixaplication.api.retrofit.ApiConfig
import com.example.mytraffixaplication.databinding.ActivityMainBinding
import com.example.mytraffixaplication.ui.profile.ProfileActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPref: SharedPreferences

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvCardInfo.layoutManager = layoutManager

        sharedPref = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)

        val jwtToken = sharedPref.getString("jwt_token", "")
        Log.d("token = ", jwtToken.toString())

        val currentUser = FirebaseAuth.getInstance().currentUser
        currentUser?.let { user ->
            val email = user.email
            val username = extractUsernameFromEmail(email)

            binding.helloUser.text = "Hello $username"
        }

        getHomePageInfo()
        setAction()
    }

    private fun extractUsernameFromEmail(email: String?): String {
        val atIndex = email?.indexOf("@")
        if (atIndex != -1) {
            return email?.substring(0, atIndex!!) ?: ""
        }
        return email ?: ""
    }

    private fun getHomePageInfo() {
        showLoading(true)
        val client = ApiConfig.getApiService().getHomepage()
        client.enqueue(object : Callback<TraffixResponse>{
            override fun onResponse(
                call: Call<TraffixResponse>,
                response: Response<TraffixResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    val responseBody = response.body()
                    responseBody?.let {
                        val data = it.data
                        val suhu = data.weather.temperature.toString()
                        val icon = data.weather.icon_url
                        setHomePageData(suhu, icon)
                        setCardInfoData(data.list_atcs)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<TraffixResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    @SuppressLint("SetTextI18n")
    private fun setHomePageData(cuaca: String, icon: String){
        binding.tvSuhu.text = "$cuaca°"
        Glide.with(this)
            .load(icon)
            .into(binding.ivWeather)
    }

    private fun setCardInfoData(atcs: List<Atcs>){
        val listAtcsInfo = ArrayList<Atcs>()
        for (atcsItem in atcs){
            listAtcsInfo.add(atcsItem)
        }
        val adapter = CardInfoAdapter(listAtcsInfo)
        binding.rvCardInfo.adapter = adapter
    }

    private fun setAction() {
        binding.circleImageView.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }
    private fun showLoading(isLoading: Boolean){
        if (isLoading){
            binding.progressCircular.visibility = View.VISIBLE
        } else {
            binding.progressCircular.visibility = View.GONE
        }
    }

}
