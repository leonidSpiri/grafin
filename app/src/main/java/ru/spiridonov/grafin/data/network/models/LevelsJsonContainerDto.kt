package ru.spiridonov.grafin.data.network.models

import com.google.gson.JsonArray
import com.google.gson.annotations.Expose

data class LevelsJsonContainerDto(
    @Expose
    val json: JsonArray? = null
)
