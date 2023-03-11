package ru.spiridonov.grafin.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LevelsInfoDto(
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("description")
    @Expose
    val description: String
)
