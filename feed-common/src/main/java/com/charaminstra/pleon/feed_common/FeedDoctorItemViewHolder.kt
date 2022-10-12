package com.charaminstra.pleon.feed_common

import com.charaminstra.pleon.feed_common.databinding.ItemFeedDoctorBinding
import com.charaminstra.pleon.foundation.model.ViewObject
import java.text.SimpleDateFormat

class FeedDoctorItemViewHolder(
    private val binding: ItemFeedDoctorBinding
): FeedCommonViewHolder(binding){
    private lateinit var dateFormat: SimpleDateFormat

    override fun bind(item: ViewObject, onClickFeed: (String) -> Unit)  {
        binding.prescriptionPatientName.text = item.plant.name
        binding.solutionNum.text = 1.toString()
        binding.solutionTxt.text = item.causes?.get(0)?.guide
        //binding.solutionPreview.text = item.causes?.get(0)?.guide
   //     binding.symptomRecyclerview.adapter =
//        Glide.with(binding.root).load(item.image_url).into(binding.symptomImg)
//        binding.symptomTxt.text = item.symptoms.symptom_ko
        //dateFormat = SimpleDateFormat(binding.root.context.resources.getString(com.charaminstra.pleon.common.R.string.date_view_format))
//        binding.feedContent.text = item.content
//        binding.plantTagTv.text = binding.root.context.resources.getString(R.string.plant_tag)+ item.plant.name!!
//        for(i in ActionType.values()){
//            if(i.action == item.kind){
//                binding.actionTagTv.text = binding.root.context.resources.getString(R.string.action_tag)+i.name
//            }
//        }
//        if(item.image_url != null) {
//            binding.plantImage.visibility = View.VISIBLE
//            Glide.with(binding.root).load(item.image_url).into(binding.plantImage)
//        }else{
//            binding.plantImage.visibility = View.GONE
//        }
        //binding.feedDate.text = dateFormat.format(item.publish_date)

        //user data
//        binding.userName.text = item.user.nickname
//        Glide.with(binding.root).load(item.user.thumbnail).into(binding.userImage)

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

//        binding.root.setOnClickListener {
//            Log.i("feed id in feed viewholder", item.id)
//            onClickFeed(item.id)
//        }
    }

}
