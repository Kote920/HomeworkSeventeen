package com.example.homeworkseventeen.presentation.splash

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.homeworkseventeen.databinding.FragmentSplashBinding
import com.example.homeworkseventeen.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    private val viewModel: SplashViewModel by viewModels()


    override fun setUp() {
        viewModel.readSession()
        bindObserves()
    }

    override fun listeners() {

    }

    override fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.sessionFlow.collect{

                    delay(2000)
                    openSpecificFragment(it[0], it[1].toBoolean())

                }
            }
        }
    }

    private fun openLoginFragment(){
        findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLogInFragment())

    }

    private fun openSpecificFragment(session: String, remember: Boolean){
        if (!session.isNullOrEmpty() && remember){

            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment(session))
        }else{
            openLoginFragment()
        }

    }

}
