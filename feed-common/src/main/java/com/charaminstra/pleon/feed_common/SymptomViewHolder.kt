package com.charaminstra.pleon.feed_common

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.charaminstra.pleon.feed_common.databinding.ItemSymptomBinding
import com.charaminstra.pleon.foundation.model.SymptomObject

class SymptomViewHolder(val binding: ItemSymptomBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: SymptomObject, position: Int) {
        Glide.with(binding.root).load(item.image_url).into(binding.symptomImg)
        binding.symptomTxt.text = item.symptom_ko
//        binding.symptomImg
//        binding.causeNum.text = (position+1).toString()
//        binding.causeTxt.text = item.cause_ko
    }
}