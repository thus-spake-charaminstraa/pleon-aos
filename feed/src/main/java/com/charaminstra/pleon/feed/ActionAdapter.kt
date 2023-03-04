package com.charaminstra.pleon.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.charaminstra.pleon.common.data.ActionData
import com.charaminstra.pleon.feed.databinding.ItemActionBinding

class ActionAdapter(): RecyclerView.Adapter<ActionViewHolder>() {

    var viewItemList: List<ActionData> = listOf()
    var onItemClicked: (ActionData)-> Unit = {}

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
//                onItemClicked(item.actionType)
                onItemClicked(item)
                notifyItemChanged(selectedPosition)
                notifyItemChanged(position)
                selectedPosition=position
        }
    }

    fun refreshItems(viewItemList : List<ActionData>) {
        this.viewItemList = viewItemList
        notifyDataSetChanged() // Andoid RecyclerView DiffUtil.
    }

}
