package ru.spiridonov.grafin.presentation

import android.content.Context
import androidx.fragment.app.Fragment
import ru.spiridonov.grafin.GrafinApp

class GameFinishedFragment:Fragment() {

    private val component by lazy {
        (requireActivity().application as GrafinApp).component
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }
}