package com.charaminstra.pleon.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.charaminstra.pleon.PlantViewHolderFactory
import com.charaminstra.pleon.foundation.model.PlantDataObject
import com.charaminstra.pleon.viewholder.CommonViewHolder

class CommonAdapter(private val viewItemList: List<PlantDataObject>,
                    private val type: String): RecyclerView.Adapter<CommonViewHolder>()  {
    override fun getItemViewType(position: Int): Int {
        return CommonViewType.valueOf(type).ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder {
        return PlantViewHolderFactory.createViewHolder(parent, viewType)
    }

    override fun getItemCount(): Int {
        return viewItemList.size
    }
    override fun onBindViewHolder(holder: CommonViewHolder, position: Int) {
        holder.bind(viewItemList[position])
    }
}
