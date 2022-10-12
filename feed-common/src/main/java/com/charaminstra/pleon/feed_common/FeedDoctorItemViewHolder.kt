package com.charaminstra.pleon.feed_common

import com.charaminstra.pleon.feed_common.databinding.ItemFeedDoctorBinding
import com.charaminstra.pleon.foundation.model.ViewObject
import java.text.SimpleDateFormat

class FeedDoctorItemViewHolder(
    private val binding: ItemFeedDoctorBinding
): FeedCommonViewHolder(binding){
    private lateinit var dateFormat: SimpleDateFormat

    override fun bind(item: ViewObject,viewType:Int, onClickFeed: (Int, String) -> Unit)  {
        binding.prescriptionPatientName.text = item.plant.name
        binding.solutionNum.text = 1.toString()
        binding.solutionTxt.text = item.causes?.get(0)?.guide

        dateFormat = SimpleDateFormat(binding.root.context.resources.getString(com.charaminstra.pleon.common.R.string.date_view_format))

        binding.feedDate.text = dateFormat.format(item.created_at)

        binding.root.setOnClickListener {
            onClickFeed(viewType, item.id)
        }

        //comment
//        val count = item.comments?.size
//        if(count == 0) {
//            binding.icComment.visibility = View.GONE
//            binding.feedCommentCount.visibility = View.GONE
//        }else{
//            binding.icComment.visibility = View.VISIBLE
//            binding.feedCommentCount.visibility = View.VISIBLE
//            binding.feedCommentCount.text = count.toString()
//        }


    }

}
