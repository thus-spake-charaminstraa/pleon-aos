package com.charaminstra.pleon.feed_common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.charaminstra.pleon.feed_common.databinding.ItemFeedBinding
import com.charaminstra.pleon.feed_common.databinding.ItemFeedDoctorBinding
import com.charaminstra.pleon.common.ResultObject

class FeedAdapter(): RecyclerView.Adapter<FeedCommonViewHolder>() {

    var fromView = ""
    var viewItemList = mutableListOf<ResultObject>()

    override fun getItemViewType(position: Int): Int {
        return FeedViewType.valueOf(viewItemList[position].viewType).ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedCommonViewHolder {
        return when(viewType){
            FeedViewType.FEED.ordinal -> FeedItemViewHolder(ItemFeedBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            FeedViewType.DIAGNOSIS.ordinal -> FeedDoctorItemViewHolder(ItemFeedDoctorBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> throw IllegalArgumentException("Unknown view type")
        }
    }

    override fun getItemCount(): Int {
        return viewItemList.size
    }
    override fun onBindViewHolder(holder: FeedCommonViewHolder, position: Int) {
        holder.bind(viewItemList[position].viewObject, fromView)
    }

    fun initItems(viewItemList : List<ResultObject>){
        this.viewItemList.clear()
        this.viewItemList.addAll(viewItemList)
        notifyDataSetChanged()
    }

    fun clearItems(){
        viewItemList.clear()
    }
}
