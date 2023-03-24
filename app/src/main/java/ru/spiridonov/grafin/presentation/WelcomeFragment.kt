package ru.spiridonov.grafin.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.spiridonov.grafin.GrafinApp
import ru.spiridonov.grafin.R
import ru.spiridonov.grafin.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {
    private var _binding: FragmentWelcomeBinding? = null
    private val binding: FragmentWelcomeBinding
        get() = _binding ?: throw RuntimeException("FragmentWelcomeBinding == null")

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
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonClickListener()
    }

    private fun buttonClickListener() = with(binding) {
        buttonUnderstand.setOnClickListener {
            navigateToFragment(R.id.action_welcomeFragment_to_chooseLevelFragment)
        }
        buttonAwards.setOnClickListener {
            navigateToFragment(R.id.action_welcomeFragment_to_awardsFragment)
        }
        buttonLoyalty.setOnClickListener {
            navigateToFragment(R.id.action_welcomeFragment_to_loyaltyFragment)
        }
        buttonProfile.setOnClickListener {
            navigateToFragment(R.id.action_welcomeFragment_to_profileFragment)
        }
    }

    private fun navigateToFragment(id: Int) = findNavController().navigate(id)

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}