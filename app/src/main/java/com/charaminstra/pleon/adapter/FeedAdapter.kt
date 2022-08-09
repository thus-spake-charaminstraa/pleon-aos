package com.charaminstra.pleon.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.charaminstra.pleon.databinding.ItemFeedBinding
import com.charaminstra.pleon.databinding.ItemLoadingBinding
import com.charaminstra.pleon.foundation.model.FeedObject
import com.charaminstra.pleon.viewholder.*

class FeedAdapter(): RecyclerView.Adapter<FeedItemCommonViewHolder>() {

    companion object {
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_LOADING = 1
    }

    var viewItemList: List<FeedObject> = listOf()
    var onItemClicked: (String)-> Unit = {}

    override fun getItemViewType(position: Int): Int {
        // 게시물과 프로그레스바 아이템뷰를 구분할 기준이 필요하다.
        return when (viewItemList[position]) {
            null -> VIEW_TYPE_LOADING
            else -> VIEW_TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedItemCommonViewHolder {
        if (viewType == VIEW_TYPE_ITEM){
            return FeedItemViewHolder((ItemFeedBinding.inflate(LayoutInflater.from(parent.context), parent, false))){}
        } else{
            return FeedItemLoadingViewHolder((ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false))){}
        }
    }

    override fun onBindViewHolder(holder: FeedItemCommonViewHolder , position: Int) {
        holder.bind(viewItemList[position]!!)
        holder.itemView.setOnClickListener {
            onItemClicked(viewItemList[position]?.id!!)
        }
    }

    override fun getItemCount(): Int {
        return viewItemList.size
    }

    fun refreshItems(viewItemList : List<FeedObject>) {
        this.viewItemList = viewItemList
        notifyDataSetChanged() // Andoid RecyclerView DiffUtil.
    }

    fun addItems(newViewItemList : List<FeedObject>?){
        this.viewItemList += newViewItemList!!
    }
}
