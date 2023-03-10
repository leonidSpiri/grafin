package ru.spiridonov.grafin.domain.repository

import ru.spiridonov.grafin.domain.entity.GameSettings
import ru.spiridonov.grafin.domain.entity.Level
import ru.spiridonov.grafin.domain.entity.Question

interface GameRepository {
    fun getListQuestions(level: Level): List<Question>
    fun getGameSettings(level: Level): GameSettings
}