package com.bashirli.weather.ui.fragment.city

import android.graphics.Canvas
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bashirli.weather.R
import com.bashirli.weather.base.BaseFragment
import com.bashirli.weather.databinding.FragmentCityBinding
import com.bashirli.weather.generateCityToken
import com.bashirli.weather.model.local.SavedCity
import com.bashirli.weather.model.token.CityTokenManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import javax.inject.Inject

@AndroidEntryPoint
class CityFragment : BaseFragment<FragmentCityBinding>(FragmentCityBinding::inflate) {

    private val viewModel:CityMVVM by viewModels<CityMVVM>()
    private val adapter= CityAdapter()

    @Inject
    lateinit var  tokenManager:CityTokenManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
        observeData()
    }

    private fun setup(){
        viewModel.getData()
        with(binding){
            rvCities.layoutManager=LinearLayoutManager(requireContext())
            rvCities.adapter=adapter

            adapter.onClickItem={
                configure(it)
            }

            editSearchText.setOnClickListener {
                val extras= FragmentNavigatorExtras(editSearch to "searchLayout")
                findNavController().navigate(CityFragmentDirections.actionCityFragmentToSearchCityFragment(),extras)
            }

        }
        setRvDecorator()
    }

    private fun observeData(){
        with(viewModel){
            cityData.observe(viewLifecycleOwner){
                adapter.updateList(it)
            }
        }
    }

    private fun configure(item:SavedCity){
        val token= generateCityToken(item.cityLat,item.cityLong)
        tokenManager.removeToken()
        tokenManager.addCityToken(token)
        findNavController().navigate(CityFragmentDirections.actionCityFragmentToHomeFragment(token))
    }

    private fun setRvDecorator(){
        val savedToken=tokenManager.getCityToken()

        val callback=object:ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position=viewHolder.bindingAdapterPosition
                val currentItem=adapter.getItemFromAdapterList(position)
                val latLong= generateCityToken(currentItem.cityLat,currentItem.cityLong)



                viewModel.deleteCity(currentItem)
                adapter.removeItemFromAdapterList(position)

                if(latLong==savedToken){
                    tokenManager.removeToken()
                }

                Snackbar.make(requireView(),R.string.itemRemoved,Snackbar.LENGTH_LONG).setAction(R.string.undo){

                    viewModel.insertData(currentItem)
                    adapter.insertItemToAdapterList(position,currentItem)

                    if(latLong==savedToken){
                        tokenManager.addCityToken(latLong)
                    }

                }.show()
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
               RecyclerViewSwipeDecorator.Builder(
                   c,recyclerView,viewHolder,dX, dY, actionState, isCurrentlyActive
               ).addBackgroundColor(ContextCompat.getColor(requireContext(),R.color.red))
                   .addActionIcon(R.drawable.baseline_delete_forever_24)
                   .addCornerRadius(0,15)
                   .create()
                   .decorate()
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )

            }
        }

        val itemTouchHelper=ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.rvCities)
    }

}