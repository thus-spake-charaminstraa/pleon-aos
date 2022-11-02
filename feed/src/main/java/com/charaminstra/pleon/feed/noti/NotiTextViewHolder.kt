package com.charaminstra.pleon.feed.noti

import com.bumptech.glide.Glide
import com.charaminstra.pleon.feed.databinding.ItemNotiTextBinding
import com.charaminstra.pleon.foundation.model.NotiData


class NotiTextViewHolder (private val binding: ItemNotiTextBinding): NotiCommonViewHolder(binding) {
    override fun bind(item: NotiData) {
        Glide.with(binding.root).load(item.plant?.thumbnail).circleCrop().into(binding.notiAuthorThumbnail)
        binding.notiTitle.text = item.content
        binding.commentContent.text = item.comment?.content
        binding.notiFeedPreview.text = item.feedContent
//        binding.notiCompleteBtn.setOnClickListener {
//            onClickNoti(item.id, NOTI_COMPLETE)
//        }
    }
}