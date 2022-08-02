package com.charaminstra.pleon

import android.view.LayoutInflater
import android.view.ViewGroup
import com.charaminstra.pleon.adapter.PlantViewType
import com.charaminstra.pleon.databinding.ItemPlantFeedBinding
import com.charaminstra.pleon.databinding.ItemPlantGardenBinding
import com.charaminstra.pleon.viewholder.FeedPlantViewHolder
import com.charaminstra.pleon.viewholder.GardenPlantViewHolder
import com.charaminstra.pleon.viewholder.CommonViewHolder

object PlantViewHolderFactory {
    fun createViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder {
        return when (viewType){
            PlantViewType.FEED_PLANT.ordinal -> FeedPlantViewHolder(ItemPlantFeedBinding.inflate(LayoutInflater.from(parent.context),
                parent, false)){}
            PlantViewType.GARDEN_PLANT.ordinal -> GardenPlantViewHolder(ItemPlantGardenBinding.inflate(LayoutInflater.from(parent.context),
                parent, false)){ }
            else -> throw IllegalArgumentException("Unknown view type")
        }
    }
}

