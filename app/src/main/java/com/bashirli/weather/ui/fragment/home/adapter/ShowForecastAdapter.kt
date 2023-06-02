package com.bashirli.weather.ui.fragment.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bashirli.weather.databinding.ItemForecastBinding
import com.bashirli.weather.databinding.ItemShowForecastBinding
import com.bashirli.weather.model.weather.Forecastday
import com.bashirli.weather.ui.fragment.forecast.ForecastAdapter

class ShowForecastAdapter : RecyclerView.Adapter<ShowForecastAdapter.ShowForecastViewHolder>() {

    private val arrayList=ArrayList<Forecastday>()

    inner class ShowForecastViewHolder(val binding: ItemShowForecastBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: Forecastday){
            binding.forecastDay=item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowForecastViewHolder {
        val layout= ItemShowForecastBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ShowForecastViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ShowForecastViewHolder, position: Int) {
        val item=arrayList.get(position)
        holder.bind(item)
    }

    fun updateList(list:List<Forecastday>){
        arrayList.clear()
        arrayList.addAll(list)
        notifyDataSetChanged()
    }

}