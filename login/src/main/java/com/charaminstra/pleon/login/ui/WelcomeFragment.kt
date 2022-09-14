package com.charaminstra.pleon.login.ui

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.charaminstra.pleon.login.R
import com.charaminstra.pleon.login.startHomeActivity

class WelcomeFragment : Fragment() {
    private val TAG = javaClass.simpleName
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = com.charaminstra.pleon.login.databinding.FragmentWelcomeBinding.inflate(inflater, container, false)
        val foregroundSpan = ForegroundColorSpan(resources.getColor(com.charaminstra.pleon.common_ui.R.color.main_green_color))
        val string = SpannableString(binding.welcomeTv.text)
        string.setSpan(
            foregroundSpan,
            24,29, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        val styleSpan = StyleSpan(Typeface.BOLD)
        string.setSpan(
            styleSpan,
            24,29, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.welcomeTv.text = string
        binding.startBtn.setOnClickListener {
            startHomeActivity(requireContext())
        }
        return binding.root
    }
}