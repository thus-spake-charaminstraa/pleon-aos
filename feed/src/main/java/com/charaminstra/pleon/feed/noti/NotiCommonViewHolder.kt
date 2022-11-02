package com.charaminstra.pleon.feed.noti

import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.charaminstra.pleon.foundation.model.GuideData
import com.charaminstra.pleon.foundation.model.NotiData

abstract class NotiCommonViewHolder (binding: ViewBinding): RecyclerView.ViewHolder(binding.root) {

    open fun bind(viewObject: NotiData) {}
}

//class PlantViewHolder(
//    private val binding: ListItemPlantBinding
//) : RecyclerView.ViewHolder(binding.root) {
//    init {
//        binding.setClickListener {
//            binding.plant?.let { plant ->
//                navigateToPlant(plant, it)
//            }
//        }
//    }
//
//    private fun navigateToPlant(
//        plant: Plant,
//        view: View
//    ) {
//        val direction =
//            HomeViewPagerFragmentDirections.actionViewPagerFragmentToPlantDetailFragment(
//                plant.plantId
//            )
//        view.findNavController().navigate(direction)
//    }
//
//    fun bind(item: Plant) {
//        binding.apply {
//            plant = item
//            executePendingBindings()
//        }
//    }
//}