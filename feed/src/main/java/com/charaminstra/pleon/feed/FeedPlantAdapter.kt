package com.charaminstra.pleon.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.charaminstra.pleon.common.CommonViewHolder
import com.charaminstra.pleon.feed.databinding.ItemPlantFilterBinding
import com.charaminstra.pleon.foundation.model.PlantDataObject

class FeedPlantAdapter(): RecyclerView.Adapter<CommonViewHolder>() {

    var viewItemList: List<PlantDataObject> = listOf()
    private lateinit var type: String
    var onItemClicked: (String)-> Unit = {}

//    override fun getItemViewType(position: Int): Int {
//        return PlantViewType.valueOf(type).ordinal
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder {
        return FeedPlantViewHolder(
            ItemPlantFilterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false))
    }

    override fun getItemCount(): Int {
        return viewItemList.size
    }

    override fun onBindViewHolder(holder: CommonViewHolder, position: Int) {
        holder.bind(viewItemList[position],onItemClicked)
    }

    fun refreshItems(viewItemList : List<PlantDataObject>) {
        this.viewItemList = viewItemList
        notifyDataSetChanged() // Andoid RecyclerView DiffUtil.
    }

    fun setType(type: String) {
        this.type = type
    }

}
