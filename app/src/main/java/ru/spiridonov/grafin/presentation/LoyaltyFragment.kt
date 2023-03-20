package ru.spiridonov.grafin.presentation

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import ru.spiridonov.grafin.GrafinApp
import ru.spiridonov.grafin.R
import ru.spiridonov.grafin.databinding.FragmentChooseLevelBinding
import ru.spiridonov.grafin.databinding.FragmentLoyaltyBinding
import ru.spiridonov.grafin.presentation.viewmodels.AwardsViewModel
import ru.spiridonov.grafin.presentation.viewmodels.MainViewModel
import ru.spiridonov.grafin.presentation.viewmodels.ViewModelFactory
import javax.inject.Inject

class LoyaltyFragment : Fragment() {

    private var _binding: FragmentLoyaltyBinding? = null
    private val binding: FragmentLoyaltyBinding
        get() = _binding ?: throw RuntimeException("FragmentLoyaltyBinding == null")


    private val component by lazy {
        (requireActivity().application as GrafinApp).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: AwardsViewModel

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoyaltyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[AwardsViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}