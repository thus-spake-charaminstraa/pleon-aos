package com.charaminstra.pleon.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.charaminstra.pleon.databinding.ItemFeedBinding
import com.charaminstra.pleon.databinding.ItemPlantGardenBinding
import com.charaminstra.pleon.foundation.model.FeedDataObject
import com.charaminstra.pleon.foundation.model.ResultObject

class FeedViewHolder(
    private val binding: ItemFeedBinding,
    private var onItemClicked: (String) -> Unit): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ResultObject) {
        binding.feedContent.text = item.content
        binding.plantTagTv.text = "@"+item.plant.name!!
        binding.actionTagTv.text = "#"+item.kind
        if(item.image_url != null) {
            binding.plantImage.visibility = View.VISIBLE
            Glide.with(binding.root).load(item.image_url).into(binding.plantImage)
        }else{
            binding.plantImage.visibility = View.GONE
        }
    }

}
