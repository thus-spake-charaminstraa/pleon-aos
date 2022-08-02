package com.charaminstra.pleon.adapter

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.charaminstra.pleon.PlantViewHolderFactory
import com.charaminstra.pleon.foundation.model.PlantDataObject
import com.charaminstra.pleon.viewholder.CommonViewHolder

class CommonAdapter(): RecyclerView.Adapter<CommonViewHolder>() {

    var viewItemList: List<PlantDataObject> = listOf()
    private lateinit var type: String
    var onItemClicked: (String)-> Unit = {}


    override fun getItemViewType(position: Int): Int {
        //viewItemList[position].viewType
        return CommonViewType.valueOf(type).ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder {
//        if(viewType == "gallery_thumbnail"){
//
//        }
        return PlantViewHolderFactory.createViewHolder(parent, viewType)
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
