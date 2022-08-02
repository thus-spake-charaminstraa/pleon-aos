package com.charaminstra.pleon.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.charaminstra.pleon.databinding.ItemFeedBinding
import com.charaminstra.pleon.databinding.ItemPlantFeedBinding
import com.charaminstra.pleon.databinding.ItemPlantGardenBinding
import com.charaminstra.pleon.foundation.model.FeedDataObject
import com.charaminstra.pleon.foundation.model.PlantDataObject
import com.charaminstra.pleon.viewholder.CommonViewHolder
import com.charaminstra.pleon.viewholder.FeedPlantViewHolder
import com.charaminstra.pleon.viewholder.FeedViewHolder
import com.charaminstra.pleon.viewholder.GardenPlantViewHolder

class FeedAdapter(): RecyclerView.Adapter<FeedViewHolder>() {

    var viewItemList: List<FeedDataObject> = listOf()
    var onItemClicked: (String)-> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        return FeedViewHolder((ItemFeedBinding.inflate(LayoutInflater.from(parent.context), parent, false))){}
    }

    override fun getItemCount(): Int {
        return viewItemList.size
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.bind(viewItemList[position])
        holder.itemView.setOnClickListener {
            onItemClicked(viewItemList[position].id!!)
        }
    }

    fun refreshItems(viewItemList : List<FeedDataObject>) {
        this.viewItemList = viewItemList
        notifyDataSetChanged() // Andoid RecyclerView DiffUtil.
    }

}
