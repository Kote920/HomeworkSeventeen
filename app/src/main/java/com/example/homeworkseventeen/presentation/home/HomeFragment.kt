package com.example.homeworkseventeen.presentation.home

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.homeworkseventeen.databinding.FragmentHomeBinding
import com.example.homeworkseventeen.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {



    val args: HomeFragmentArgs by navArgs()
    private val viewModel: HomeViewModel by viewModels()


    override fun setUp() {

        binding.tvEmail.text = args.email


    }

    override fun listeners() {

        binding.btnBack.setOnClickListener {
            viewModel.killSession()
            openLogIn()
        }

    }

    override fun bindObserves() {

    }
    private fun openLogIn() {
        val action = HomeFragmentDirections.actionHomeFragmentToLogInFragment(checker = false)
        findNavController().navigate(action)
    }



}