package com.bashirli.weather.ui.fragment.city

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bashirli.weather.databinding.ItemSavedCitiesBinding
import com.bashirli.weather.model.local.SavedCity

class CityAdapter : RecyclerView.Adapter<CityAdapter.CityViewHolder>() {
    private val arrayList=ArrayList<SavedCity>()

    var onClickItem:(SavedCity)->Unit={}

    inner class CityViewHolder(val binding:ItemSavedCitiesBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item:SavedCity){
            binding.savedCity=item
        }
        fun find(item:SavedCity,onClickItem:(SavedCity)->Unit={}){
            binding.cardView.setOnClickListener {
                onClickItem(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val layout=ItemSavedCitiesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CityViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val item=arrayList.get(position)
        holder.bind(item)
        holder.find(item,onClickItem)
    }

    fun updateList(list:List<SavedCity>){
        arrayList.clear()
        arrayList.addAll(list)
        notifyDataSetChanged()
    }

    fun getItemFromAdapterList(position: Int):SavedCity{
        return arrayList.get(position)
    }

    fun removeItemFromAdapterList(position: Int){
        arrayList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun insertItemToAdapterList(position: Int,savedCity: SavedCity){
        arrayList.add(position,savedCity)
        notifyItemInserted(position)
    }

}