package ru.spiridonov.grafin.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.spiridonov.grafin.data.memory.LevelsObjects
import ru.spiridonov.grafin.domain.entity.GameResult
import ru.spiridonov.grafin.domain.entity.Level
import ru.spiridonov.grafin.domain.entity.Question
import ru.spiridonov.grafin.domain.usecases.GetListQuestionsUseCase
import ru.spiridonov.grafin.domain.usecases.LoadDataUseCase
import ru.spiridonov.grafin.util.SharedPref
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val loadDataUseCase: LoadDataUseCase,
    private val sharedPref: SharedPref,
    getListQuestionsUseCase: GetListQuestionsUseCase
) : ViewModel() {

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

    private val _needToShowError = MutableLiveData<Boolean>()
    val needToShowError: LiveData<Boolean>
        get() = _needToShowError

    private val _levelsList = MutableLiveData<List<Level>>()
    val levelsList: LiveData<List<Level>>
        get() = _levelsList

    private val _questionsList = MutableLiveData<List<Question>>()
    val questionsList: LiveData<List<Question>>
        get() = _questionsList

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult


    fun loadData() = loadDataUseCase.invoke { result ->
        _isDataLoadedCorrect.value = result
        if (result) {
            _levelsList.value = LevelsObjects.levelsArray
        }
    }

    private val getListQuestions = getListQuestionsUseCase
    private var countOfAnsweredQuestions = 0
    private var countOfRightAnsweredQuestions = 0
    private var levelId = 0

    fun chooseAnswer(number: Int) {
        val isAnswerCorrect = checkAnswer(number)
        if (countOfAnsweredQuestions == getListQuestions.invoke(levelId).size) {
            finishGame()
            return
        }
        _needToShowError.value = !isAnswerCorrect
        if (isAnswerCorrect)
            generateQuestion()
    }

    private fun finishGame() {
        val isWin = countOfRightAnsweredQuestions >= countOfAnsweredQuestions - 2
        val result = GameResult(
            isWin,
            countOfRightAnsweredQuestions,
            countOfAnsweredQuestions,
            levelId
        )
        _gameResult.value = result
        sharedPref.addScore(result.countOfRightAnswers)
    }

    fun startGame(levelId: Int) {
        this.levelId = levelId
        _questionsList.value = getListQuestions.invoke(levelId)
        countOfAnsweredQuestions = 0
        countOfRightAnsweredQuestions = 0
        generateQuestion()
        _questionsCount.value = 0
    }

    fun getLevelsList() {
        _levelsList.value = LevelsObjects.levelsArray
    }

    fun generateQuestion() {
        _question.value = getListQuestions.invoke(levelId)[countOfAnsweredQuestions]
    }

    private fun checkAnswer(answer: Int): Boolean {
        var isAnswerRight = false
        val rightAnswer = getListQuestions.invoke(levelId)[countOfAnsweredQuestions].rightAnswer
        if (rightAnswer == answer) {
            countOfRightAnsweredQuestions++
            isAnswerRight = true
        }
        countOfAnsweredQuestions++
        _rightQuestionsCount.value = countOfRightAnsweredQuestions
        _questionsCount.value = countOfAnsweredQuestions
        return isAnswerRight
    }
}