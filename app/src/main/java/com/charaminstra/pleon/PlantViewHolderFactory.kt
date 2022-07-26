package com.charaminstra.pleon

import android.view.LayoutInflater
import android.view.ViewGroup
import com.charaminstra.pleon.databinding.ItemPlantGardenBinding
import com.charaminstra.pleon.viewholder.GardenPlantViewHolder
import com.charaminstra.pleon.viewholder.PlantViewHolder

object PlantViewHolderFactory {
    fun createViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        return when (viewType){
            //PlantsViewType.FEED.ordinal -> OneLineViewHolder(ItemOneLineBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            PlantsViewType.GARDEN.ordinal -> GardenPlantViewHolder(ItemPlantGardenBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
            else -> throw IllegalArgumentException("Unknown view type")
        }
    }
}

