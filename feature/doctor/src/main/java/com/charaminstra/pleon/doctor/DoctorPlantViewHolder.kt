package com.charaminstra.pleon.doctor

import com.bumptech.glide.Glide
import com.charaminstra.pleon.feed_common.PlantCommonViewHolder
import com.charaminstra.pleon.doctor.databinding.ItemDoctorPlantBinding
import com.charaminstra.pleon.domain.model.Plant

class DoctorPlantViewHolder(
    val binding: ItemDoctorPlantBinding
): PlantCommonViewHolder(binding) {

    override fun bind(item: Plant, onItemClicked: (String) -> Unit) {
        binding.doctorPlantName.text = item.plantName
        Glide.with(binding.root).load(item.plantImageUrl).into(binding.doctorPlantImg)
        binding.doctorPlantRoot.setOnClickListener {
            onItemClicked(item.plantId!!)
        }
    }
}