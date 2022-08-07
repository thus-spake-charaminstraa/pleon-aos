package com.charaminstra.pleon.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.charaminstra.pleon.databinding.ItemFeedBinding
import com.charaminstra.pleon.foundation.model.ResultObject
import java.text.SimpleDateFormat

class FeedItemViewHolder(
    private val binding: ItemFeedBinding,
    private var onItemClicked: (String) -> Unit): FeedItemCommonViewHolder(binding) {

    val dateFormat = SimpleDateFormat("yyyy-MM-dd")

    override fun bind(item: ResultObject) {
        binding.feedContent.text = item.content
        binding.plantTagTv.text = "@"+item.plant.name!!
        binding.actionTagTv.text = "#"+item.kind
        if(item.image_url != null) {
            binding.plantImage.visibility = View.VISIBLE
            Glide.with(binding.root).load(item.image_url).into(binding.plantImage)
        }else{
            binding.plantImage.visibility = View.GONE
        }
        /*
        binding.feedDate.text = dateFormat.format(item.publish_date)
         */
        binding.feedDate.text =dateFormat.parse(item.publish_date).toString()
    }

}
