package ru.spiridonov.grafin.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.spiridonov.grafin.util.SharedPref
import javax.inject.Inject

class AwardsViewModel @Inject constructor(
    private val sharedPref: SharedPref
) : ViewModel() {

    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score


    init {
        _score.value = sharedPref.getScore()
    }

}