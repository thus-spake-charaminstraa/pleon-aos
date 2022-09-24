package com.charaminstra.pleon.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.charaminstra.pleon.databinding.ItemCommentBinding
import com.charaminstra.pleon.foundation.model.CommentObject
import com.charaminstra.pleon.viewholder.CommentViewHolder

class _CommentsAdapter(): RecyclerView.Adapter<CommentViewHolder>() {

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