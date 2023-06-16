package com.example.mytraffixaplication.api.retrofit.remote

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class HomePageResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("status")
	val status: Int? = null
) : Parcelable

@Parcelize
data class Info(

	@field:SerializedName("is_monitoring")
	val isMonitoring: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("nama_atcs")
	val namaAtcs: String? = null,

	@field:SerializedName("id_atcs")
	val idAtcs: String? = null,

	@field:SerializedName("lat")
	val lat: String? = null,

	@field:SerializedName("long")
	val jsonMemberLong: String? = null,

	@field:SerializedName("stream_url")
	val streamUrl: String? = null
) : Parcelable

@Parcelize
data class Weather(

	@field:SerializedName("icon_url")
	val iconUrl: String? = null,

	@field:SerializedName("temperature")
	val temperature: Double? = null,

	@field:SerializedName("is_day")
	val isDay: Int? = null,

	@field:SerializedName("weather_code")
	val weatherCode: String? = null
) : Parcelable

@Parcelize
data class Statistik(
	val any: String? = null
) : Parcelable

@Parcelize
data class ListAtcsItem(

	@field:SerializedName("statistik")
	val statistik: Statistik? = null,

	@field:SerializedName("info")
	val info: Info? = null
) : Parcelable

@Parcelize
data class Data(

	@field:SerializedName("list_atcs")
	val listAtcs: List<ListAtcsItem?>? = null,

	@field:SerializedName("weather")
	val weather: Weather? = null
) : Parcelable
