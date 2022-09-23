package com.charaminstra.pleon.feed

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.charaminstra.pleon.common.ActionObject
import com.charaminstra.pleon.common.ActionType
import com.charaminstra.pleon.feed.databinding.ItemActionBinding

class ActionViewHolder(val binding: ItemActionBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ActionObject,selectedPosition:Int,position: Int, onItemClicked: (ActionType) -> Unit) {
        binding.actionName.text = item.actionType.toString()
        binding.actionImg.setOnClickListener {
            onItemClicked(item.actionType)

            Log.i("position",position.toString())
            //Log.i("selected position",selectedPosition.toString())
//            Log.i("selectPosition 전 ",selectPosition.toString())
//            selectPosition = position
            Log.i("selectPosition 후",selectedPosition.toString()+"\n")

        }
        Glide.with(binding.root).load(item.actionImage).into(binding.actionImg)
    }
}