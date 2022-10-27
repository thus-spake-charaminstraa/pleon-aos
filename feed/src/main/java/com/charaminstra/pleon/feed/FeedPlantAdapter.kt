package com.charaminstra.pleon.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.charaminstra.pleon.feed.databinding.ItemPlantBinding
import com.charaminstra.pleon.foundation.model.PlantDataObject

class FeedPlantAdapter(): RecyclerView.Adapter<FeedPlantViewHolder>() {

    var viewItemList: List<PlantDataObject> = listOf()
    var onItemClicked: (String, String) -> Unit = { s: String, s1: String -> }
    var selectedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedPlantViewHolder {
        return FeedPlantViewHolder(
            ItemPlantBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return viewItemList.size
    }

    override fun onBindViewHolder(holder: FeedPlantViewHolder, position: Int) {
        holder.bind(viewItemList[position])
        if(selectedPosition==position) {
            holder.binding.root.isSelected = true
        }else{
            holder.binding.root.isSelected = false
        }
        holder.binding.plantFilterRoot.setOnClickListener {
            notifyItemChanged(selectedPosition)
            notifyItemChanged(position)
            selectedPosition=position
            onItemClicked(viewItemList[position].id!!,viewItemList[position].name!!)
        }
    }


    fun refreshClick(){
        notifyItemChanged(selectedPosition)
        selectedPosition = -1
    }

    fun refreshItems(viewItemList : List<PlantDataObject>) {
        this.viewItemList = viewItemList
        notifyDataSetChanged() // Andoid RecyclerView DiffUtil.
    }

//    fun setType(type: String) {
//        this.type = type
//    }

}
