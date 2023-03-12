package ru.spiridonov.grafin.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.spiridonov.grafin.GrafinApp
import ru.spiridonov.grafin.R
import ru.spiridonov.grafin.databinding.FragmentChooseLevelBinding
import ru.spiridonov.grafin.domain.entity.Level
import ru.spiridonov.grafin.presentation.viewmodels.MainViewModel
import ru.spiridonov.grafin.presentation.viewmodels.ViewModelFactory
import javax.inject.Inject

class ChooseLevelFragment : Fragment() {
    private var _binding: FragmentChooseLevelBinding? = null
    private val binding: FragmentChooseLevelBinding
        get() = _binding ?: throw RuntimeException("FragmentChooseLevelBinding == null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: MainViewModel

    private val component by lazy {
        (requireActivity().application as GrafinApp).component
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseLevelBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }
    private fun observeViewModel() {
        viewModel.getLevelsList()
        viewModel.levelsList.observe(viewLifecycleOwner) {
            createButtons(it)
        }
    }

    private fun createButtons(levelList: List<Level>) {
        val layout = binding.llChooseLevel
        layout.removeAllViews()
        levelList.forEach { level ->
            val inflater = layoutInflater.inflate(R.layout.btn_choose_level_layout, layout, false)
            val buttonLayout = inflater.findViewById<Button>(R.id.button).apply {
                text = level.name
                setOnClickListener { launchGameFragment(level) }
            }
            layout.addView(buttonLayout)
        }
    }

    private fun launchGameFragment(level: Level) {
        findNavController().navigate(
            ChooseLevelFragmentDirections.actionChooseLevelFragmentToGameFragment(
                level.id
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}