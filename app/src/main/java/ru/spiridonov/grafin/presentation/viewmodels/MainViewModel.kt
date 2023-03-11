package ru.spiridonov.grafin.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.spiridonov.grafin.data.memory.LevelsObjects
import ru.spiridonov.grafin.domain.entity.GameResult
import ru.spiridonov.grafin.domain.entity.Level
import ru.spiridonov.grafin.domain.entity.Question
import ru.spiridonov.grafin.domain.usecases.GetListQuestionsUseCase
import ru.spiridonov.grafin.domain.usecases.LoadDataUseCase
import javax.inject.Inject

class MainViewModel @Inject constructor(
    loadDataUseCase: LoadDataUseCase,
    getListQuestionsUseCase: GetListQuestionsUseCase
) : ViewModel() {

    private val _formattedTime = MutableLiveData<String>()
    val formattedTime: LiveData<String>
        get() = _formattedTime

    private val _questionsCount = MutableLiveData<Int>()
    val questionsCount: LiveData<Int>
        get() = _questionsCount

    private val _rightQuestionsCount = MutableLiveData<Int>()
    val rightQuestionsCount: LiveData<Int>
        get() = _rightQuestionsCount

    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    private val _isDataLoadedCorrect = MutableLiveData<Boolean>()
    val isDataLoadedCorrect: LiveData<Boolean>
        get() = _isDataLoadedCorrect

    private val _levelsList = MutableLiveData<List<Level>>()
    val levelsList: LiveData<List<Level>>
        get() = _levelsList

    private val _progressAnswers = MutableLiveData<String>()
    val progressAnswers: LiveData<String>
        get() = _progressAnswers

    private val _questionsList = MutableLiveData<List<Question>>()
    val questionsList: LiveData<List<Question>>
        get() = _questionsList

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult


    val loadData = loadDataUseCase.invoke { result ->
        if (result) {
            _levelsList.value = LevelsObjects.levelsArray
        }
    }

    private val getListQuestions = getListQuestionsUseCase
    private var countOfAnsweredQuestions = 0
    private var countOfRightAnsweredQuestions = 0
    private var levelId = 0

    fun chooseAnswer(number: Int) {
        checkAnswer(number)
        if (countOfAnsweredQuestions == getListQuestions.invoke(levelId).size){
            finishGame()
            return
        }
        generateQuestion()
    }

    private fun finishGame() {
        Log.d("finish", "finish")
        Log.d("finish", countOfAnsweredQuestions.toString())
        Log.d("finish", countOfRightAnsweredQuestions.toString())
        Log.d("finish", "finish")
    }


    fun startGame(levelId: Int) {
        this.levelId = levelId
       _questionsList.value = getListQuestions.invoke(levelId)
        countOfAnsweredQuestions = 0
        countOfRightAnsweredQuestions = 0
        generateQuestion()
        _questionsCount.value = 0
    }

    private fun generateQuestion(){
        _question.value = getListQuestions.invoke(levelId)[countOfAnsweredQuestions]
    }

    private fun checkAnswer(answer:Int){
        val rightAnswer = getListQuestions.invoke(levelId)[countOfAnsweredQuestions].rightAnswer
        if (rightAnswer == answer)
            countOfRightAnsweredQuestions++
        countOfAnsweredQuestions++
        _rightQuestionsCount.value = countOfRightAnsweredQuestions
        _questionsCount.value = countOfAnsweredQuestions
    }
}