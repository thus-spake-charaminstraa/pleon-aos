package com.charaminstra.pleon.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.charaminstra.pleon.common.PlantCommonViewHolder
import com.charaminstra.pleon.feed.databinding.ItemPlantFilterBinding
import com.charaminstra.pleon.foundation.model.PlantDataObject

class FeedPlantAdapter(): RecyclerView.Adapter<PlantCommonViewHolder>() {

    var viewItemList: List<PlantDataObject> = listOf()
//    private lateinit var type: String
    var onItemClicked: (String)-> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantCommonViewHolder {
        return FeedPlantViewHolder(
            ItemPlantFilterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return viewItemList.size
    }

    override fun onBindViewHolder(holder: PlantCommonViewHolder, position: Int) {
        holder.bind(viewItemList[position],onItemClicked)
    }

    fun refreshItems(viewItemList : List<PlantDataObject>) {
        this.viewItemList = viewItemList
        notifyDataSetChanged() // Andoid RecyclerView DiffUtil.
    }

//    fun setType(type: String) {
//        this.type = type
//    }

}
