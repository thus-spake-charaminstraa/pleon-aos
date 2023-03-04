package com.charaminstra.pleon.feed_common

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.charaminstra.pleon.feed_common.databinding.ItemCommentBinding
import com.charaminstra.pleon.common.data.CommentObject

class CommentsAdapter(): RecyclerView.Adapter<CommentViewHolder>() {

    private var viewItemList: List<CommentObject> = listOf()

    override fun getItemCount(): Int {
        return viewItemList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder((ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)))
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(viewItemList[position])
    }

    fun refreshItems(viewItemList : List<CommentObject>) {
        Log.i("refresh comment", this.viewItemList.toString())
        this.viewItemList = viewItemList
        Log.i("refresh comment", this.viewItemList.toString())
        notifyDataSetChanged() // Andoid RecyclerView DiffUtil.
    }
}