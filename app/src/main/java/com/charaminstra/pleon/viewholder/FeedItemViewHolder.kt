package com.charaminstra.pleon.viewholder

import android.util.Log
import android.view.View
import androidx.core.view.setPadding
import com.bumptech.glide.Glide
import com.charaminstra.pleon.R
import com.charaminstra.pleon.databinding.ItemFeedBinding
import com.charaminstra.pleon.foundation.model.ViewObject
import java.text.SimpleDateFormat

class FeedItemViewHolder(
    private val binding: ItemFeedBinding): FeedCommonViewHolder(binding){
    private lateinit var dateFormat: SimpleDateFormat

    override fun bind(item: ViewObject, onClickFeed: (String) -> Unit, onClickNoti: (String, String) -> Unit)  {
        dateFormat = SimpleDateFormat(binding.root.context.resources.getString(com.charaminstra.pleon.common_ui.R.string.date_format))
        binding.feedContent.text = item.content
        binding.plantTagTv.text = binding.root.context.resources.getString(R.string.plant_tag)+ item.plant.name!!
        binding.actionTagTv.text = binding.root.context.resources.getString(R.string.action_tag)+ item.kind
        if(item.image_url != null) {
            binding.plantImage.visibility = View.VISIBLE
            Glide.with(binding.root).load(item.image_url).into(binding.plantImage)
        }else{
            binding.plantImage.visibility = View.GONE
        }
        binding.feedDate.text = dateFormat.format(item.publish_date)

        //user data
        binding.userName.text = item.user.nickname
        Glide.with(binding.root).load(item.user.thumbnail).into(binding.userImage)
        binding.userImage.setPadding(0)

        //comment
        val count = item.comments?.size
        if(count == 0) {
            binding.feedCommentCount.text = "no comment"
            //binding.feedCommentCount.visibility = View.GONE
            binding.firstCommentText.visibility = View.GONE
            binding.firstCommentUserName.visibility = View.GONE
            binding.firstCommentUserImage.visibility = View.GONE
        }else{
            binding.feedCommentCount.visibility = View.VISIBLE
            binding.feedCommentCount.text = "댓글 "+count.toString()+"개 모두 보기"

            //first comment
            binding.firstCommentText.text = item.comments?.get(0)?.content
            if(item.comments?.get(0)?.author_kind == "user"){
                binding.firstCommentUserName.text = item.comments?.get(0)?.user?.nickname
                Glide.with(binding.root)
                    .load(item.user?.thumbnail)
                    .into(binding.firstCommentUserImage)
            }else{
                binding.firstCommentUserName.text = item.plant?.name
                Glide.with(binding.root)
                    .load(item.plant?.thumbnail)
                    .into(binding.firstCommentUserImage)
            }
        }


        binding.root.setOnClickListener {
            Log.i("feed id in feed viewholder", item.id)
            onClickFeed(item.id)
        }
    }

}
