package com.example.mytraffixaplication.api.retrofit.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AtcsResponse(
    @field:SerializedName("id_atcs")
    val id_atcs: String? = null
): Parcelable
