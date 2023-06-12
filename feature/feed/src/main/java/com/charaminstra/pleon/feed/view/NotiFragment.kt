package com.charaminstra.pleon.feed.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.charaminstra.pleon.common.CLASS_NAME
import com.charaminstra.pleon.common.NOTI_VIEW
import com.charaminstra.pleon.feed.databinding.FragmentNotiBinding
import com.charaminstra.pleon.feed.noti.NotiAdapter
import com.charaminstra.pleon.feed.viewmodel.NotiViewModel
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotiFragment : Fragment() {
    private val TAG = javaClass.name

    private lateinit var binding: FragmentNotiBinding
    private val viewModel: NotiViewModel by viewModels()

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAnalytics= FirebaseAnalytics.getInstance(requireContext())

        // logging
        val bundle = Bundle()
        bundle.putString(CLASS_NAME, TAG)
        firebaseAnalytics.logEvent(NOTI_VIEW, bundle)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotiBinding.inflate(layoutInflater)

        binding.notiBackBtn.setOnClickListener {
            this.findNavController().popBackStack()
        }

        val adapter = NotiAdapter()
        binding.notiRecyclerview.adapter = adapter
        subscribeUi(adapter)

        return binding.root
    }

    private fun subscribeUi(adapter: NotiAdapter) {
        viewModel.notiList.observe(viewLifecycleOwner) { notifications ->
            adapter.submitList(notifications)
        }
    }

}