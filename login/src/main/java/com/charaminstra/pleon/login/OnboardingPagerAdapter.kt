package com.charaminstra.pleon.login

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class OnboardingPagerAdapter() : RecyclerView.Adapter<OnboardingPagerAdapter.PagerViewHolder>() {
    inner class PagerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder
        (LayoutInflater.from(parent.context).inflate(R.layout.item_onboarding, parent, false)) {
        val image = itemView.findViewById<ImageView>(R.id.image)
    }

    val OnboardingImage = arrayOf(R.drawable.onboarding1,R.drawable.onboarding2,R.drawable.onboarding3,R.drawable.onboarding4,R.drawable.onboarding5)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder = PagerViewHolder((parent))


    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
       holder.image.setImageResource(OnboardingImage[position])
    }

    override fun getItemCount(): Int =  OnboardingImage.size


}