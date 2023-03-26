package ru.spiridonov.grafin.presentation

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.spiridonov.grafin.GrafinApp
import ru.spiridonov.grafin.R
import ru.spiridonov.grafin.databinding.FragmentTheoryBinding
import ru.spiridonov.grafin.domain.entity.Level
import ru.spiridonov.grafin.domain.usecases.GetGameLevelUseCase
import javax.inject.Inject


class TheoryFragment : Fragment() {


    private var _binding: FragmentTheoryBinding? = null
    private val binding: FragmentTheoryBinding
        get() = _binding ?: throw RuntimeException("FragmentTheoryBinding == null")

    private val args by navArgs<TheoryFragmentArgs>()

    private val component by lazy {
        (requireActivity().application as GrafinApp).component
    }

    @Inject
    lateinit var getGameLevelUseCase: GetGameLevelUseCase

    private var levelId = 0

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTheoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        levelId = args.levelId
        val textId = when (levelId) {
            0 -> R.string.theory_0
            1 -> R.string.theory_1
            2 -> R.string.theory_2
            3 -> R.string.theory_3
            4 -> R.string.theory_4
            5 -> R.string.theory_5
            else -> R.string.theory_5
        }
        binding.tvTheory.text = resources.getText(textId)
        binding.buttonUnderstand.setOnClickListener {
            val level = getGameLevelUseCase.invoke(levelId)
            if (level != null)
                launchGameFragment(level)
        }
    }

    private fun launchGameFragment(level: Level) {
        findNavController().navigate(
            TheoryFragmentDirections.actionTheoryFragmentToGameFragment(
                level.id
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}