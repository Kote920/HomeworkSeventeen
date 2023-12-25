package com.example.homeworkseventeen.presentation.register

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.homeworkseventeen.databinding.FragmentRegisterBinding
import com.example.homeworkseventeen.data.resource.Resource
import com.example.homeworkseventeen.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
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
