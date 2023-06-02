package com.bashirli.weather.ui.fragment.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bashirli.weather.databinding.ItemSearchBinding
import com.bashirli.weather.model.search.SearchResponseItem

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private val arrayList=ArrayList<SearchResponseItem>()
    var onClickCityItem:(SearchResponseItem)->Unit={}

    inner class SearchViewHolder(val binding:ItemSearchBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item:SearchResponseItem){
            binding.item=item
        }
        fun find(item:SearchResponseItem,onClickCityItem:(SearchResponseItem)->Unit={}){
            binding.buttonAdd.setOnClickListener {
                onClickCityItem(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val layout=ItemSearchBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SearchViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item=arrayList.get(position)
        holder.bind(item)
        holder.find(item,onClickCityItem)
    }

     fun updateList(list:List<SearchResponseItem>){
        arrayList.clear()
        arrayList.addAll(list)
        notifyDataSetChanged()
    }

}