package ru.spiridonov.grafin.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.spiridonov.grafin.GrafinApp
import ru.spiridonov.grafin.databinding.FragmentGameBinding
import ru.spiridonov.grafin.domain.entity.GameResult
import ru.spiridonov.grafin.presentation.viewmodels.MainViewModel
import ru.spiridonov.grafin.presentation.viewmodels.ViewModelFactory
import javax.inject.Inject

class GameFragment : Fragment() {

    private val args by navArgs<GameFragmentArgs>()

    private val component by lazy {
        (requireActivity().application as GrafinApp).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: MainViewModel

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        observeViewModel()
        clickListener()
    }

    private fun clickListener() {
        with(binding){
            tvOption1.setOnClickListener {
                viewModel!!.chooseAnswer(0)
            }
            tvOption2.setOnClickListener {
                viewModel!!.chooseAnswer(1)
            }
            tvOption3.setOnClickListener {
                viewModel!!.chooseAnswer(2)
            }
            tvOption4.setOnClickListener {
                viewModel!!.chooseAnswer(3)
            }
        }
    }

    private fun observeViewModel() {
        viewModel.startGame(args.levelId)
        viewModel.gameResult.observe(viewLifecycleOwner) {
            launchGameFinishedFragment(it)
        }
    }

    private fun launchGameFinishedFragment(gameResult: GameResult) {
        findNavController().navigate(
            GameFragmentDirections.actionGameFragmentToGameFinishedFragment(gameResult)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}