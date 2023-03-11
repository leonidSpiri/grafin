package ru.spiridonov.grafin.data.network

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import retrofit2.http.GET

interface ApiService {
    @GET("api/levels")
    suspend fun getLevelsList(
    ): JsonArray

    @GET("api/questions")
    suspend fun getQuestionsList(
    ): JsonArray
}