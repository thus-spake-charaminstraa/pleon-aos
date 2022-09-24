package com.charaminstra.pleon.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.charaminstra.pleon.common.ActionObject
import com.charaminstra.pleon.common.ActionType
import com.charaminstra.pleon.feed.databinding.ItemActionBinding

class ActionAdapter(): RecyclerView.Adapter<ActionViewHolder>() {

    var viewItemList: List<ActionObject> = listOf()
    var onItemClicked: (ActionType)-> Unit = {}

    var selectedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionViewHolder {
        return ActionViewHolder((ItemActionBinding.inflate(LayoutInflater.from(parent.context), parent, false)))
    }
    override fun getItemCount(): Int {
        return viewItemList.size
    }
    override fun onBindViewHolder(holder: ActionViewHolder, position: Int) {
        holder.bind(viewItemList[position])
        val item = viewItemList[position]
        if(selectedPosition==position) {
            holder.binding.actionImg.isSelected = true
        }else{
            holder.binding.actionImg.isSelected = false
        }
        holder.itemView.setOnClickListener {
                onItemClicked(item.actionType)
                notifyItemChanged(selectedPosition)
                notifyItemChanged(position)
                selectedPosition=position
        }
    }

    fun refreshItems(viewItemList : List<ActionObject>) {
        this.viewItemList = viewItemList
        //notifyDataSetChanged() // Andoid RecyclerView DiffUtil.
    }

}
