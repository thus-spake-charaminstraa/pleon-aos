package com.charaminstra.pleon.common_ui

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

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

@BindingAdapter("plantTag")
fun bindPlantTag(view: TextView, plantName: String?) {
    view.text = "@"+plantName
}

@BindingAdapter("actionTag")
fun bindActionTag(view: TextView, resId: Int?) {
    resId?.let {
        view.text = "#"+view.context.getString(it)
    }
}

@BindingAdapter("date")
fun bindDate(view: TextView, date: Date?) {
    date?.let {
        val dateFormat = SimpleDateFormat(view.resources.getString(R.string.date_view_format))
        view.text = dateFormat.format(date)
    }
    Log.i("BindingAdapter",date.toString())

}