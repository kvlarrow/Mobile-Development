package com.example.mytraffixaplication.api.response

data class TraffixResponse(
    val `data`: Data,
    val status: Int
)

data class Data(
    val list_atcs: List<Atcs>,
    val weather: Weather
)

data class Atcs(
    val info: Info,
    val statistik: List<Statistik>
)

data class Statistik(
    val bus: Int,
    val car: Int,
    val createdAt: String,
    val data_in: Int,
    val data_out: Int,
    val motorcycle: Int,
    val truck: Int
)

data class Info(
    val id_atcs: String,
    val lat: String,
    val long: String,
    val nama_atcs: String
)

data class Weather(
    val icon_url: String,
    val is_day: Int,
    val temperature: Double,
    val weather_code: String
)