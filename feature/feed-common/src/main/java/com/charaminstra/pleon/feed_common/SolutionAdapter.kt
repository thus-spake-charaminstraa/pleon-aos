package com.charaminstra.pleon.feed_common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.charaminstra.pleon.common.model.CauseObject
import com.charaminstra.pleon.feed_common.databinding.ItemSolutionBinding

class SolutionAdapter(): RecyclerView.Adapter<SolutionViewHolder>() {

    var viewItemList: List<CauseObject> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SolutionViewHolder {
        return SolutionViewHolder((ItemSolutionBinding.inflate(LayoutInflater.from(parent.context), parent, false)))
    }
    override fun getItemCount(): Int {
        return viewItemList.size
    }
    override fun onBindViewHolder(holder: SolutionViewHolder, position: Int) {
        holder.bind(viewItemList[position], position)
    }

    fun setItemList(viewItemList : List<CauseObject>) {
        this.viewItemList = viewItemList
        notifyDataSetChanged()
    }

}
