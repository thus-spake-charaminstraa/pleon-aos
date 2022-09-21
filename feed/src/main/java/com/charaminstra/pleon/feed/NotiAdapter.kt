package com.charaminstra.pleon.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.charaminstra.pleon.common.PlantCommonViewHolder
import com.charaminstra.pleon.feed.databinding.ItemNotiBinding
import com.charaminstra.pleon.feed.databinding.ItemPlantFilterBinding
import com.charaminstra.pleon.foundation.model.NotiData
import com.charaminstra.pleon.foundation.model.PlantDataObject

class NotiAdapter(): RecyclerView.Adapter<NotiViewHolder>() {

    var viewItemList: List<NotiData> = listOf()
    var onItemClicked: (String)-> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotiViewHolder {
        return NotiViewHolder(
            ItemNotiBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return viewItemList.size
    }

    override fun onBindViewHolder(holder: NotiViewHolder, position: Int) {
        holder.bind(viewItemList[position],onItemClicked)
    }

    fun refreshItems(viewItemList : List<NotiData>) {
        this.viewItemList = viewItemList
        notifyDataSetChanged() // Andoid RecyclerView DiffUtil.
    }
}
