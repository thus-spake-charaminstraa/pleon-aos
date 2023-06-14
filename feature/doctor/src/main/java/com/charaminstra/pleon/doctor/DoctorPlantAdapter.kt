package com.charaminstra.pleon.doctor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.charaminstra.pleon.doctor.databinding.ItemDoctorPlantBinding
import com.charaminstra.pleon.domain.model.Plant

class DoctorPlantAdapter(): RecyclerView.Adapter<DoctorPlantViewHolder>() {

    var viewItemList: List<Plant> = listOf()
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
            onItemClicked(viewItemList[position].plantId!!)
        }
    }

    fun refreshItems(viewItemList: List<Plant>) {
        this.viewItemList = viewItemList
        notifyDataSetChanged() // Andoid RecyclerView DiffUtil.
    }

}
