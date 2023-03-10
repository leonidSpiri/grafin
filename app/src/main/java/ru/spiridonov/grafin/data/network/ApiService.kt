package ru.spiridonov.grafin.data.network

import retrofit2.http.GET
import ru.spiridonov.grafin.data.network.models.LevelsJsonContainerDto
import ru.spiridonov.grafin.data.network.models.QuestionsJsonContainerDto

interface ApiService {
    @GET("api/levels.json")
    suspend fun getLevelsList(
    ): LevelsJsonContainerDto

    @GET("api/questions.json")
    suspend fun getQuestionsList(
    ): QuestionsJsonContainerDto
}