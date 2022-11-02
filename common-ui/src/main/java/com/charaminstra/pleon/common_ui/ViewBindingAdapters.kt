package com.charaminstra.pleon.common_ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imgUrl")
fun bindImgUrl(view: ImageView, imgUrl: String?) {
    Glide.with(view.context)
        .load(imgUrl)
        .into(view)
}

@BindingAdapter("circleImgUrl")
fun bindCircleImgUrl(view: ImageView, imgUrl: String?) {
    Glide.with(view.context)
        .load(imgUrl)
        .circleCrop()
        .into(view)
}