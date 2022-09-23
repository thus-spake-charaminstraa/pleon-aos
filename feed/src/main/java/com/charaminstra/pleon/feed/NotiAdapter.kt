package com.charaminstra.pleon.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.charaminstra.pleon.feed.databinding.ItemNotiBinding
import com.charaminstra.pleon.foundation.model.NotiData
import com.charaminstra.pleon.foundation.model.PlantDataObject

class NotiAdapter(): RecyclerView.Adapter<NotiViewHolder>() {

    var viewItemList: List<NotiData> = listOf()
    var onClickNoti: (String, String)-> Unit = { s1: String, s2: String -> }

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
        holder.bind(viewItemList[position],onClickNoti)
    }

    fun refreshItems(viewItemList : List<NotiData>) {
        this.viewItemList = viewItemList
        notifyDataSetChanged() // Andoid RecyclerView DiffUtil.
    }
}
