package com.bashirli.weather.ui.fragment.search

import android.content.Context
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bashirli.weather.Status

import com.bashirli.weather.base.BaseFragment
import com.bashirli.weather.databinding.FragmentSearchCityBinding
import com.bashirli.weather.generateCityToken
import com.bashirli.weather.model.local.SavedCity
import com.bashirli.weather.model.search.SearchResponse
import com.bashirli.weather.model.search.SearchResponseItem
import com.bashirli.weather.model.token.CityTokenManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SearchCityFragment : BaseFragment<FragmentSearchCityBinding>(FragmentSearchCityBinding::inflate) {

    @Inject
    lateinit var tokenManager: CityTokenManager

    private val viewModel:SearchMVVM by viewModels<SearchMVVM>()
    private val adapter=SearchAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val anim=TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition=anim
        sharedElementReturnTransition=anim
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
        observeData()
    }

    private fun setup(){
        with(binding){
            editSearchText.requestFocus()
            val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm!!.showSoftInput(editSearchText, InputMethodManager.SHOW_IMPLICIT)

            buttonGoBack.setOnClickListener {
                findNavController().popBackStack()
            }

            rvCities.layoutManager=LinearLayoutManager(requireContext())
            rvCities.adapter=adapter

            adapter.onClickCityItem={
                configureData(it)
            }

            editSearchText.addTextChangedListener {
                it?.let {
                    lifecycleScope.launch{
                        val text=it.toString()
                        if(text.isNotEmpty()){
                            delay(500)
                            viewModel.searchCity(text)
                        }
                    }
                }
            }
        }
    }

    private fun observeData(){
        with(viewModel) {
        with(binding){
            loading.observe(viewLifecycleOwner){
                if(it){
                    progressBar.visibility=View.VISIBLE
                }
            }
            searchData.observe(viewLifecycleOwner){
                it?.let {
                    progressBar.visibility=View.GONE
                    when(it.status){
                        Status.SUCCESS->{
                            it.data?.let {
                                setData(it)
                            }
                        }
                        Status.ERROR->{
                            Toast.makeText(requireContext(),it.message?:"Error",Toast.LENGTH_SHORT).show()
                        }
                        else->{}
                    }
                }
            }
        }

        }
    }

    private fun setData(item:SearchResponse){
        adapter.updateList(item)
    }

    private fun configureData(item:SearchResponseItem){
        val token= generateCityToken(item.lat,item.lon)
        tokenManager.removeToken()
        tokenManager.addCityToken(token)
        with(item){
            val savedCity=SavedCity(
                cityLat = lat,
                cityLong = lon,
                cityName = name,
                cityRegion = region,
                country = country
            )
            viewModel.addCity(savedCity)
        }
        findNavController().navigate(SearchCityFragmentDirections.actionSearchCityFragmentToHomeFragment(token))
    }

}