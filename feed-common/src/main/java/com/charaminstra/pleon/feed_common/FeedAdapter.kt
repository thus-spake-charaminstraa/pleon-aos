package com.charaminstra.pleon.feed_common

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.charaminstra.pleon.common.databinding.ItemFeedBinding
import com.charaminstra.pleon.feed_common.databinding.ItemFeedDoctorBinding
import com.charaminstra.pleon.feed_common.databinding.ItemFeedLoadingBinding
import com.charaminstra.pleon.foundation.model.ResultObject

class FeedAdapter(): RecyclerView.Adapter<FeedCommonViewHolder>() {

    var viewItemList = ArrayList<ResultObject>()
    var onClickFeed: (Int, String)-> Unit = { i: Int, s: String -> }

    override fun getItemViewType(position: Int): Int {
        return FeedViewType.valueOf(viewItemList[position].viewType).ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedCommonViewHolder {
        return when(viewType){
            FeedViewType.FEED.ordinal -> FeedItemViewHolder(ItemFeedBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            FeedViewType.DIAGNOSIS.ordinal -> FeedDoctorItemViewHolder(ItemFeedDoctorBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            FeedViewType.LOADING.ordinal -> LoadingItemViewHolder(ItemFeedLoadingBinding.inflate(LayoutInflater.from(parent.context),parent,false))
            else -> throw IllegalArgumentException("Unknown view type")
        }
    }

    override fun getItemCount(): Int {
        return viewItemList.size
    }
    override fun onBindViewHolder(holder: FeedCommonViewHolder, position: Int) {
        holder.bind(viewItemList[position].viewObject, getItemViewType(position), onClickFeed)
    }

    fun initItems(viewItemList : ArrayList<ResultObject>){
        this.viewItemList=viewItemList
        notifyDataSetChanged()
    }

    fun addItemsAndLoading(viewItemList : ArrayList<ResultObject>) {
        viewItemList.add(ResultObject("LOADING", null))
        this.viewItemList.addAll(viewItemList)
        notifyDataSetChanged() // Andoid RecyclerView DiffUtil.
    }

    fun addFinalItems(viewItemList : ArrayList<ResultObject>) {
        this.viewItemList.addAll(viewItemList)
        notifyDataSetChanged() // Andoid RecyclerView DiffUtil.
    }

//    fun refreshItems(viewItemList : ArrayList<ResultObject>) {
//        this.viewItemList.addAll(viewItemList)
//        notifyDataSetChanged() // Andoid RecyclerView DiffUtil.
//    }

    fun deleteLoading(){
        if(viewItemList[viewItemList.lastIndex].viewType == "LOADING"){
            viewItemList.removeAt(viewItemList.lastIndex)
        }
    }
}
