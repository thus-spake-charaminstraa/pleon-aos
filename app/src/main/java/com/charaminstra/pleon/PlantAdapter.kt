package com.charaminstra.pleon

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.charaminstra.pleon.foundation.model.PlantDataObject
import com.charaminstra.pleon.viewholder.PlantViewHolder

enum class PlantsViewType {
    FEED, GARDEN
}

class PlantAdapter(private val viewItemList: List<PlantDataObject>,
                   private val type: String): RecyclerView.Adapter<PlantViewHolder>()  {
    override fun getItemViewType(position: Int): Int {
        return PlantsViewType.valueOf(type).ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        return PlantViewHolderFactory.createViewHolder(parent, viewType)
    }

    override fun getItemCount(): Int {
        return viewItemList.size
    }
    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        holder.bind(viewItemList[position])
    }
}
