package com.charaminstra.pleon.plant_register

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.charaminstra.pleon.foundation.model.PlantSpeciesDataObject
import com.charaminstra.pleon.plant_register.databinding.ItemPlantSearchBinding

class PlantSearchAdapter() : RecyclerView.Adapter<PlantSearchViewHolder>() {

    var viewItemList: List<PlantSpeciesDataObject> = listOf()
    var onItemClicked: (String)-> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantSearchViewHolder {
        return PlantSearchViewHolder((ItemPlantSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false))){}
    }

    override fun getItemCount(): Int {
        return viewItemList.size
    }

    override fun onBindViewHolder(holder: PlantSearchViewHolder, position: Int) {
        holder.bind(viewItemList[position])
    }

    fun refreshItems(viewItemList : List<PlantSpeciesDataObject>) {
        this.viewItemList = viewItemList
        notifyDataSetChanged() // Andoid RecyclerView DiffUtil.
    }

}



class PlantSearchViewHolder(
    private val binding: ItemPlantSearchBinding,
    private var onItemClicked: (String) -> Unit
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: PlantSpeciesDataObject) {
        binding.plantSpecies.text= item.name
    }
}