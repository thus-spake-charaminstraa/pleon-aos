package com.charaminstra.pleon.viewholder

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.charaminstra.pleon.databinding.ItemCommentBinding
import com.charaminstra.pleon.foundation.model.CommentObject

class CommentViewHolder(
    private val binding: ItemCommentBinding
)  : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CommentObject){
        Log.i("CommentsViewHolder", "bind: $item")
        binding.commentText.text = item.content
    }
}