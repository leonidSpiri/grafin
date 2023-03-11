package ru.spiridonov.grafin.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class QuestionsInfoDto(
    @SerializedName("LevelId")
    @Expose
    val id: Int,
    @SerializedName("question")
    @Expose
    val question: String,
    @SerializedName("answers")
    @Expose
    val answers: List<String>,
    @SerializedName("true_answer")
    @Expose
    val trueAnswer: Int
)

