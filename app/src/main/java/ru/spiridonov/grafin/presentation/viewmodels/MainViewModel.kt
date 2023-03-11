package ru.spiridonov.grafin.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.spiridonov.grafin.data.memory.LevelsObjects
import ru.spiridonov.grafin.data.memory.QuestionsObjects
import ru.spiridonov.grafin.domain.entity.Level
import ru.spiridonov.grafin.domain.entity.Question
import ru.spiridonov.grafin.domain.usecases.LoadDataUseCase
import javax.inject.Inject

class MainViewModel @Inject constructor(
    loadDataUseCase: LoadDataUseCase
) : ViewModel() {

    private val _isDataLoadedCorrect = MutableLiveData<Boolean>()
    val isDataLoadedCorrect: LiveData<Boolean>
        get() = _isDataLoadedCorrect

    private val _levelsList = MutableLiveData<List<Level>>()
    val levelsList: LiveData<List<Level>>
        get() = _levelsList

    private val _questionsList = MutableLiveData<List<Question>>()
    val questionsList: LiveData<List<Question>>
        get() = _questionsList

    val loadData = loadDataUseCase.invoke { result ->
        if (result) {
            _levelsList.value = LevelsObjects.levelsArray
            _questionsList.value = QuestionsObjects.questionsArray
        }
    }
}