package com.charaminstra.pleon.doctor

import com.bumptech.glide.Glide
import com.charaminstra.pleon.common.PlantCommonViewHolder
import com.charaminstra.pleon.doctor.databinding.ItemDoctorPlantBinding
import com.charaminstra.pleon.foundation.model.PlantDataObject
import dagger.hilt.android.lifecycle.HiltViewModel

class DoctorPlantViewHolder(
    val binding: ItemDoctorPlantBinding
): PlantCommonViewHolder(binding) {

    override fun bind(item: PlantDataObject, onItemClicked: (String) -> Unit) {
        binding.doctorPlantName.text = item.name
        Glide.with(binding.root).load(item.thumbnail).into(binding.doctorPlantImg)
        binding.doctorPlantRoot.setOnClickListener {
            onItemClicked(item.id!!)
        }
    }
}