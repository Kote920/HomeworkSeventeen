package com.example.homeworkseventeen.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.homeworkseventeen.BaseFragment
import com.example.homeworkseventeen.R
import com.example.homeworkseventeen.databinding.FragmentRegisterBinding
import com.example.homeworkseventeen.resource.Resource
import kotlinx.coroutines.launch

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {
    private val viewModel: RegisterViewModel by viewModels()
    override fun setUp() {


    }

    override fun listeners() {
        binding.btnRegister.setOnClickListener {
            if (inputValidation(binding.etEmail) && inputValidation(binding.etPassword) && inputValidation(
                    binding.etRepeatPassword
                ) &&
                passwordsValidation(binding.etPassword, binding.etRepeatPassword
                        ) && emailValidation(binding.etEmail)
            ) {
                viewModel.register(
                    binding.etEmail.text.toString(),
                    binding.etPassword.text.toString()
                )
                bindObserves()
            }
        }

        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

    }

    override fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.registerFlow.collect {
                    when (it) {
                        is Resource.Loading -> {
                            binding.pbRegister.visibility = View.VISIBLE
                        }

                        is Resource.Success -> {
                            val registeredUser = it.responseData
                            binding.pbRegister.visibility = View.GONE
                            Toast.makeText(requireContext(), "Registration Success", Toast.LENGTH_SHORT).show()
                            setUpBundle(binding.etEmail.text.toString(), binding.etPassword.text.toString())
                            openLogin()
                        }

                        is Resource.Failed -> {
                            binding.pbRegister.visibility = View.GONE
                            val errorMessage = it.message
                            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT)
                                .show()

                        }
                    }
                }
            }
        }
    }

    private fun openLogin() {
        val action = RegisterFragmentDirections.actionRegisterFragmentToLogInFragment()
        findNavController().navigate(action)
    }

    private fun setUpBundle(email: String, password: String) {
        val bundle = Bundle().apply {
            putString("email", email)
            putString("password", password)
        }

        setFragmentResult("requestKey", bundle)
    }

}
