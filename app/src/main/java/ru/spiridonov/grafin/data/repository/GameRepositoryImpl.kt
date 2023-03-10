package ru.spiridonov.grafin.data.repository

import ru.spiridonov.grafin.domain.entity.GameSettings
import ru.spiridonov.grafin.domain.entity.Level
import ru.spiridonov.grafin.domain.entity.Question
import ru.spiridonov.grafin.domain.repository.GameRepository
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor() : GameRepository {
    override fun getListQuestions(level: Level): List<Question> {
        return emptyList()
    }

    override fun getGameSettings(level: Level): GameSettings = when (level.id) {
        0 -> GameSettings(10, 3, 50, 8)
        1 -> GameSettings(10, 10, 70, 60)
        2 -> GameSettings(20, 20, 80, 40)
        3 -> GameSettings(30, 30, 90, 40)
        else -> GameSettings(30, 30, 90, 40)
    }

}