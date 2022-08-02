package com.charaminstra.pleon.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.charaminstra.pleon.databinding.ItemPlantFeedBinding
import com.charaminstra.pleon.databinding.ItemPlantGardenBinding
import com.charaminstra.pleon.foundation.model.PlantDataObject
import com.charaminstra.pleon.viewholder.CommonViewHolder
import com.charaminstra.pleon.viewholder.FeedPlantViewHolder
import com.charaminstra.pleon.viewholder.GardenPlantViewHolder

class PlantAdapter(): RecyclerView.Adapter<CommonViewHolder>() {

    var viewItemList: List<PlantDataObject> = listOf()
    private lateinit var type: String
    var onItemClicked: (String)-> Unit = {}

    override fun getItemViewType(position: Int): Int {
        return PlantViewType.valueOf(type).ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder {
        return return when (viewType){
            PlantViewType.FEED_PLANT.ordinal -> FeedPlantViewHolder(
                ItemPlantFeedBinding.inflate(
                    LayoutInflater.from(parent.context),
                parent, false)){}
            PlantViewType.GARDEN_PLANT.ordinal -> GardenPlantViewHolder(
                ItemPlantGardenBinding.inflate(
                    LayoutInflater.from(parent.context),
                parent, false)){ }
            else -> throw IllegalArgumentException("Unknown view type")
        }
    }

    override fun getItemCount(): Int {
        return viewItemList.size
    }

    override fun onBindViewHolder(holder: CommonViewHolder, position: Int) {
        holder.bind(viewItemList[position])
        holder.itemView.setOnClickListener {
            onItemClicked(viewItemList[position].id!!)
        }
    }

    fun refreshItems(viewItemList : List<PlantDataObject>) {
        this.viewItemList = viewItemList
        notifyDataSetChanged() // Andoid RecyclerView DiffUtil.
    }

    fun setType(type: String) {
        this.type = type
    }

}
