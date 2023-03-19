package ru.spiridonov.grafin.util

import android.app.Application
import android.content.Context
import ru.spiridonov.grafin.GrafinApp
import javax.inject.Inject

class SharedPref @Inject constructor(
    private val application: Application
) {

    fun addScore(score: Int) {
        val currentScore = getScore()
        val newScore = currentScore + score
        application.getSharedPreferences("grafin", Context.MODE_PRIVATE).edit()
            .putInt("score", newScore).apply()
    }

    fun getScore() =
        application.getSharedPreferences("grafin", Context.MODE_PRIVATE).getInt("score", 0)

}