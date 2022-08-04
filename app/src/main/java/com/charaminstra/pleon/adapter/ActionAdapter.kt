package com.charaminstra.pleon.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.charaminstra.pleon.databinding.ItemFeedBinding
import com.charaminstra.pleon.databinding.ItemPlantFeedBinding
import com.charaminstra.pleon.foundation.model.FeedDataObject
import com.charaminstra.pleon.viewholder.ActionViewHolder
import com.charaminstra.pleon.viewholder.FeedViewHolder

class ActionAdapter(): RecyclerView.Adapter<ActionViewHolder>() {

    var viewItemList: List<ActionObject> = listOf()
    var onItemClicked: (ActionType)-> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionViewHolder {
        return ActionViewHolder((ItemPlantFeedBinding.inflate(LayoutInflater.from(parent.context), parent, false))){}
    }

    override fun getItemCount(): Int {
        return viewItemList.size
    }

    override fun onBindViewHolder(holder: ActionViewHolder, position: Int) {
        holder.bind(viewItemList[position])
        holder.itemView.setOnClickListener {
            onItemClicked(viewItemList[position].actionType)
        }
    }

    fun refreshItems(viewItemList : List<ActionObject>) {
        this.viewItemList = viewItemList
        notifyDataSetChanged() // Andoid RecyclerView DiffUtil.
    }

}
