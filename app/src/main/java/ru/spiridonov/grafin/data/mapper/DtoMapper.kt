package ru.spiridonov.grafin.data.mapper

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import ru.spiridonov.grafin.data.network.models.LevelsInfoDto
import ru.spiridonov.grafin.data.network.models.QuestionsInfoDto
import ru.spiridonov.grafin.domain.entity.Level
import ru.spiridonov.grafin.domain.entity.Question
import javax.inject.Inject

class DtoMapper @Inject constructor() {
    fun mapLevelsJsonContainerToListLevelsInfo(jsonContainer: JsonArray): List<LevelsInfoDto> {
        val result = mutableListOf<LevelsInfoDto>()
        jsonContainer.forEach {
            it.asJsonObject?.let { jsonElement ->
                val levelItem = Gson().fromJson(jsonElement, LevelsInfoDto::class.java)
                result.add(levelItem)
            }
        }
        return result
    }

    fun mapQuestionsJsonContainerToListQuestionsInfo(jsonContainer: JsonArray): List<QuestionsInfoDto> {
        val result = mutableListOf<QuestionsInfoDto>()
        jsonContainer.forEach {
            it.asJsonObject?.let { jsonElement ->
                val levelItem = Gson().fromJson(jsonElement, QuestionsInfoDto::class.java)
                result.add(levelItem)
            }
        }
        return result
    }

    fun mapLevelsJsonContainerToListLevels(levelsJsonContainer: List<LevelsInfoDto>): List<Level> {
        val result = mutableListOf<Level>()
        for (level in levelsJsonContainer)
            result.add(
                Level(
                    level.id,
                    level.name,
                    level.description
                )
            )

        return result
    }

    fun mapQuestionsJsonContainerToListQuestions(questionsJsonContainer: List<QuestionsInfoDto>): List<Question> {
        val result = mutableListOf<Question>()
        for (question in questionsJsonContainer)
            result.add(
                Question(
                    question.id,
                    question.question,
                    question.answers,
                    question.trueAnswer
                )
            )

        return result
    }
}