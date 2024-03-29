package com.charaminstra.pleon.garden

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.charaminstra.pleon.domain.model.Plant
import com.charaminstra.pleon.garden.databinding.ItemGardenPlantBinding

class GardenPlantAdapter(): RecyclerView.Adapter<GardenPlantViewHolder>() {

    var viewItemList: List<Plant> = listOf()
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

    fun refreshItems(viewItemList: List<Plant>) {
        this.viewItemList = viewItemList
        notifyDataSetChanged() // Andoid RecyclerView DiffUtil.
    }
}
