package com.bashirli.weather.ui.fragment.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bashirli.weather.databinding.ItemHourBinding
import com.bashirli.weather.model.weather.Forecast
import com.bashirli.weather.model.weather.Forecastday
import com.bashirli.weather.model.weather.Hour
import com.bashirli.weather.setImageCondition
import com.bashirli.weather.setImageURL

class HourAdapter : RecyclerView.Adapter<HourAdapter.HourViewHolder>() {

    private val arrayList=ArrayList<Hour>()

    inner class HourViewHolder(val binding:ItemHourBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item:Hour){
            binding.hour=item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourViewHolder {
        val layout=ItemHourBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HourViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: HourViewHolder, position: Int) {
        val item=arrayList.get(position)
        holder.bind(item)
    }

    fun updateList(list:List<Hour>){
        arrayList.clear()
        arrayList.addAll(list)
        notifyDataSetChanged()
    }
}