package com.example.homeworkseventeen.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.homeworkseventeen.BaseFragment
import com.example.homeworkseventeen.R
import com.example.homeworkseventeen.databinding.FragmentHomeBinding
import com.example.homeworkseventeen.login.LogInFragmentDirections
import com.example.homeworkseventeen.login.LogInViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {



    val args: HomeFragmentArgs by navArgs()


    override fun setUp() {

        binding.tvEmail.text = args.email


    }

    override fun listeners() {

        binding.btnBack.setOnClickListener {

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