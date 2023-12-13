package com.example.homeworkseventeen.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.homeworkseventeen.BaseFragment
import com.example.homeworkseventeen.R
import com.example.homeworkseventeen.SessionManager
import com.example.homeworkseventeen.databinding.FragmentLogInBinding
import com.example.homeworkseventeen.home.HomeFragmentArgs
import com.example.homeworkseventeen.resource.Resource
import kotlinx.coroutines.launch

class LogInFragment : BaseFragment<FragmentLogInBinding>(FragmentLogInBinding::inflate) {

    private val viewModel: LogInViewModel by viewModels {
        LogInViewModelFactory(SessionManager(requireContext()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Use the Kotlin extension in the fragment-ktx artifact.
        setFragmentResultListener("requestKey") { requestKey, bundle ->
            val email = bundle.getString("email")
            val password = bundle.getString("password")
            binding.etEmail.setText(email)
            binding.etPassword.setText(password)
        }
    }

    private val args: LogInFragmentArgs by navArgs()


    override fun setUp() {
        if (!args.checker) {
            viewModel.logOut()
        } else if (viewModel.checkRemember()) {

            openHome(viewModel.getEmail()!!)

        }

    }

    override fun listeners() {
        binding.btnLogIn.setOnClickListener {

            if (emailValidation(binding.etEmail) && inputValidation(binding.etPassword)) {
                viewModel.logIn("eve.holt@reqres.in", binding.etPassword.text.toString())
                binding.btnLogIn.isEnabled = false
                bindObserves()
                viewModel.setRemember(isChecked(binding.cbRememberMe))

            }


        }
        binding.etEmail.addTextChangedListener(textWatcher)
        binding.etPassword.addTextChangedListener(textWatcher)

        binding.btnRegister.setOnClickListener {
            openRegister()
        }

        binding.cbRememberMe.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                viewModel.setRemember(true)
            } else {
                viewModel.setRemember(false)
            }
        }

    }

    override fun bindObserves() {

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.logInFlow.collect() {
                    when (it) {
                        is Resource.Loading -> {
                            binding.pbLogIn.visibility = View.VISIBLE
                        }

                        is Resource.Success -> {
                            val activeUser = it.responseData
                            binding.pbLogIn.visibility = View.GONE
                            Toast.makeText(requireContext(), "log in success", Toast.LENGTH_SHORT)
                                .show()
                            openHome(viewModel.getEmail()!!)


                        }

                        is Resource.Failed -> {
                            binding.pbLogIn.visibility = View.GONE
                            val errorMessage = it.message
                            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                }
            }
        }
    }

    private fun openHome(email: String) {
        val action = LogInFragmentDirections.actionLogInFragmentToHomeFragment(email)
        findNavController().navigate(action)
    }
    private fun openRegister() {
        val action = LogInFragmentDirections.actionLogInFragmentToRegisterFragment()
        findNavController().navigate(action)
    }

    private fun isChecked(rememberBox: AppCompatCheckBox): Boolean {
        return rememberBox.isChecked
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            binding.btnLogIn.isEnabled =
                inputValidation(binding.etEmail) && inputValidation(binding.etPassword) && emailValidation(
                    binding.etEmail
                )
        }
    }


}