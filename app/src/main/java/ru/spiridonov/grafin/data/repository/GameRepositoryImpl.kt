package ru.spiridonov.grafin.data.repository

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.spiridonov.grafin.data.mapper.DtoMapper
import ru.spiridonov.grafin.data.memory.LevelsObjects
import ru.spiridonov.grafin.data.memory.QuestionsObjects
import ru.spiridonov.grafin.data.network.ApiService
import ru.spiridonov.grafin.domain.entity.Question
import ru.spiridonov.grafin.domain.repository.GameRepository
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(
    private val application: Application,
    private val apiService: ApiService,
    private val dtoMapper: DtoMapper
) : GameRepository {

    private val localBroadcastManager by lazy {
        LocalBroadcastManager.getInstance(application)
    }

    override fun getListQuestions(levelId: Int): List<Question> {
        val questions = mutableListOf<Question>()
        QuestionsObjects.questionsArray.forEach {
            if (it.levelId == levelId)
                questions.add(it)
        }
        return questions
    }

    override fun getGameLevel(id: Int) =
        LevelsObjects.levelsArray.find { it.id == id }


    override fun loadData(result: (Boolean) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {

                Log.d("getGameSettings", "start")
                Intent("loaded").apply {
                    putExtra("status", "in_progress")
                    localBroadcastManager.sendBroadcast(this)
                }
                val levelsJsonContainer = apiService.getLevelsList()
                val levelsInfoDtoList =
                    dtoMapper.mapLevelsJsonContainerToListLevelsInfo(levelsJsonContainer)
                val mapLevels = dtoMapper.mapLevelsJsonContainerToListLevels(levelsInfoDtoList)

                val questionsJsonContainer = apiService.getQuestionsList()
                val questionsInfoDtoList =
                    dtoMapper.mapQuestionsJsonContainerToListQuestionsInfo(questionsJsonContainer)
                val mapQuestions =
                    dtoMapper.mapQuestionsJsonContainerToListQuestions(questionsInfoDtoList)

                if (mapLevels.isNotEmpty() && mapQuestions.isNotEmpty()) {
                    LevelsObjects.levelsArray = mapLevels
                    QuestionsObjects.questionsArray = mapQuestions
                    Intent("loaded").apply {
                        putExtra("status", "success")
                        localBroadcastManager.sendBroadcast(this)
                    }
                    result.invoke(true)
                    return@launch
                }
                Intent("loaded").apply {
                    putExtra("status", "error")
                    localBroadcastManager.sendBroadcast(this)
                }
                result.invoke(false)
            } catch (e: Exception) {
                Log.d("getGameSettings", e.message.toString())
                Intent("loaded").apply {
                    putExtra("status", "error")
                    localBroadcastManager.sendBroadcast(this)
                }
                result.invoke(false)
            }
        }
    }

}