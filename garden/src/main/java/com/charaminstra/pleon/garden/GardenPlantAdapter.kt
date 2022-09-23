package com.charaminstra.pleon.garden

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.charaminstra.pleon.common.PlantCommonViewHolder
import com.charaminstra.pleon.foundation.model.PlantDataObject
import com.charaminstra.pleon.garden.databinding.ItemGardenPlantBinding

class GardenPlantAdapter(): RecyclerView.Adapter<GardenPlantViewHolder>() {

    var viewItemList: List<PlantDataObject> = listOf()
    var onItemClicked: (String) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GardenPlantViewHolder {
        return GardenPlantViewHolder(
            ItemGardenPlantBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return viewItemList.size
    }

    override fun onBindViewHolder(holder: GardenPlantViewHolder, position: Int) {
        holder.bind(viewItemList[position], onItemClicked)
    }

    fun refreshItems(viewItemList: List<PlantDataObject>) {
        this.viewItemList = viewItemList
        notifyDataSetChanged() // Andoid RecyclerView DiffUtil.
    }
}
