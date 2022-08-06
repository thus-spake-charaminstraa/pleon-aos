package com.charaminstra.pleon.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.charaminstra.pleon.databinding.ItemPlantFeedBinding
import com.charaminstra.pleon.viewholder.ActionViewHolder

//class CalendarAdapter(): RecyclerView.Adapter<ActionViewHolder>() {
//
//    var viewItemList: List<ActionObject> = listOf()
//    var onItemClicked: (ActionType)-> Unit = {}
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        return ActionViewHolder((CalendarDayLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))){}
//    }
//
//    override fun getItemCount(): Int {
//        return viewItemList.size
//    }
//
//    override fun onBindViewHolder(holder: ActionViewHolder, position: Int) {
//        holder.bind(viewItemList[position])
//        holder.itemView.setOnClickListener {
//            onItemClicked(viewItemList[position].actionType)
//        }
//    }
//
//    fun refreshItems(viewItemList : List<ActionObject>) {
//        this.viewItemList = viewItemList
//        notifyDataSetChanged() // Andoid RecyclerView DiffUtil.
//    }
//
//}
