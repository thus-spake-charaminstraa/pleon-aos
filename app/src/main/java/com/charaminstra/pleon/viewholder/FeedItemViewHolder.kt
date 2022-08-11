package com.charaminstra.pleon.viewholder

import android.content.res.Resources
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.charaminstra.pleon.R
import com.charaminstra.pleon.databinding.ItemFeedBinding
import com.charaminstra.pleon.foundation.model.FeedObject
import com.charaminstra.pleon.foundation.model.ResultObject
import com.charaminstra.pleon.foundation.model.ViewObject
import java.text.SimpleDateFormat
import kotlin.coroutines.coroutineContext

class FeedItemViewHolder(
    private val binding: ItemFeedBinding,
//    private var onItemClicked: (String) -> Unit): FeedItemCommonViewHolder(binding){
    private var onItemClicked: (String) -> Unit): FeedTabCommonViewHolder(binding){
    private lateinit var dateFormat: SimpleDateFormat

//    override fun bind(item: FeedObject) {
    override fun bind(item: ViewObject) {
        dateFormat = SimpleDateFormat(binding.root.context.resources.getString(com.charaminstra.pleon.common_ui.R.string.date_format))
        binding.feedContent.text = item.content
        binding.plantTagTv.text = binding.root.context.resources.getString(R.string.plant_tag)+item.plant.name!!
        binding.actionTagTv.text = binding.root.context.resources.getString(R.string.action_tag)+item.kind
        if(item.image_url != null) {
            binding.plantImage.visibility = View.VISIBLE
            Glide.with(binding.root).load(item.image_url).into(binding.plantImage)
        }else{
            binding.plantImage.visibility = View.GONE
        }
        binding.feedDate.text = dateFormat.format(item.publish_date)
    }

}
