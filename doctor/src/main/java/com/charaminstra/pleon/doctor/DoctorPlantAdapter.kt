package com.charaminstra.pleon.doctor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.charaminstra.pleon.common.data.PlantDataObject
import com.charaminstra.pleon.doctor.databinding.ItemDoctorPlantBinding

class DoctorPlantAdapter(): RecyclerView.Adapter<DoctorPlantViewHolder>() {

    var viewItemList: List<PlantDataObject> = listOf()
    var onItemClicked: (String) -> Unit = {}
    var selectedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorPlantViewHolder {
        return DoctorPlantViewHolder(
            ItemDoctorPlantBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return viewItemList.size
    }

    override fun onBindViewHolder(holder: DoctorPlantViewHolder, position: Int) {
        holder.bind(viewItemList[position], onItemClicked)
        if(selectedPosition==position) {
            holder.binding.root.isSelected = true
        }else{
            holder.binding.root.isSelected = false
        }
        holder.binding.root.setOnClickListener {
            notifyItemChanged(selectedPosition)
            notifyItemChanged(position)
            selectedPosition=position
            onItemClicked(viewItemList[position].id!!)
        }
    }

    fun refreshItems(viewItemList: List<PlantDataObject>) {
        this.viewItemList = viewItemList
        notifyDataSetChanged() // Andoid RecyclerView DiffUtil.
    }

}
