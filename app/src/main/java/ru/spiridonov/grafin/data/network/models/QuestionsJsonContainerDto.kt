package ru.spiridonov.grafin.data.network.models

import com.google.gson.JsonArray
import com.google.gson.annotations.Expose

data class QuestionsJsonContainerDto(
    @Expose
    val json: JsonArray? = null
)
