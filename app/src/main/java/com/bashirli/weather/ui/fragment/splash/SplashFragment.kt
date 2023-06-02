package com.bashirli.weather.ui.fragment.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bashirli.weather.R
import com.bashirli.weather.base.BaseFragment
import com.bashirli.weather.databinding.FragmentSplashBinding
import com.bashirli.weather.model.token.CityTokenManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    @Inject
    lateinit var cityTokenManager: CityTokenManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }


    private fun setup(){
        val token=cityTokenManager.getCityToken()
        lifecycleScope.launch {
            delay(600)
            if(token!=null){
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment(token))
            }else{
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToCityFragment())
            }
        }
    }

}