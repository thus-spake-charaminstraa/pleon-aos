package com.charaminstra.pleon.feed_common

import com.charaminstra.pleon.feed_common.databinding.ItemFeedDoctorBinding
import com.charaminstra.pleon.common.FeedViewObject
import java.text.SimpleDateFormat

class FeedDoctorItemViewHolder(
    private val binding: ItemFeedDoctorBinding
): FeedCommonViewHolder(binding){
    private lateinit var dateFormat: SimpleDateFormat

    override fun bind(item: FeedViewObject?, viewType:Int, onClickFeed: (Int, String) -> Unit)  {
        binding.plantTagTv.text = binding.root.context.resources.getString(R.string.plant_tag)+item?.plant?.name!!

        dateFormat = SimpleDateFormat(binding.root.context.resources.getString(com.charaminstra.pleon.common.R.string.date_view_format))

        binding.feedDate.text = dateFormat.format(item.created_at)

        var symptomList = ""
        for (i in item.symptoms?.indices!!){
            if(i == item.symptoms?.size?.minus(1)){
                symptomList += item.symptoms?.get(i)?.symptom_ko
            }else{
                symptomList += item.symptoms?.get(i)?.symptom_ko+"    "
            }
        }
        binding.symptomTxt.text = symptomList

        var solutionList = ""
        for (i in item.causes?.indices!!){
            if(i == item.causes?.size?.minus(1)){
                solutionList += item.causes?.get(i)?.guide
            }else{
                solutionList += item.causes?.get(i)?.guide+"\n"
            }

        }
        binding.solutionTxt.text = solutionList

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
