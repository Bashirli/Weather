package com.bashirli.weather.ui.fragment.forecast

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bashirli.weather.databinding.ItemForecastBinding
import com.bashirli.weather.model.weather.Forecastday

class ForecastAdapter : RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

    private val arrayList=ArrayList<Forecastday>()

    inner class ForecastViewHolder(val binding:ItemForecastBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item:Forecastday){
            binding.forecastDay=item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val layout=ItemForecastBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ForecastViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val item=arrayList.get(position)
        holder.bind(item)
    }

    fun updateList(list:List<Forecastday>){
        arrayList.clear()
        arrayList.addAll(list)
        notifyDataSetChanged()
    }

}