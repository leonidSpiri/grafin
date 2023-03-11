package ru.spiridonov.grafin.domain.repository

import ru.spiridonov.grafin.domain.entity.Level
import ru.spiridonov.grafin.domain.entity.Question

interface GameRepository {
    fun getListQuestions(levelId: Int): List<Question>

    fun getGameLevel(id: Int): Level?

    fun loadData(result: (Boolean) -> Unit)
}