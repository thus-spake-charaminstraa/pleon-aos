package com.charaminstra.pleon.my

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.charaminstra.pleon.common.CLASS_NAME
import com.charaminstra.pleon.common.LOGOUT_BTN_CLICK
import com.charaminstra.pleon.common.MY_VIEW
import com.charaminstra.pleon.my.databinding.FragmentMyBinding
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyFragment : Fragment() {
    private val TAG = javaClass.name
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    private val viewModel: MyViewModel by viewModels()
    private lateinit var binding : FragmentMyBinding
    private lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAnalytics= FirebaseAnalytics.getInstance(requireContext())

        // logging
        val loggingBundle = Bundle()
        loggingBundle.putString(CLASS_NAME, TAG)
        firebaseAnalytics.logEvent(MY_VIEW , loggingBundle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initObservers()
        navController = this.findNavController()

        binding = FragmentMyBinding.inflate(inflater, container, false).also {
            it.viewModel = viewModel
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.commentSwitchBtn.setOnClickListener {
            viewModel.setCommentSetting(binding.commentSwitchBtn.isChecked)
            viewModel.postPushSetting()
        }
        binding.guideSwitchBtn.setOnClickListener {
            viewModel.setGuideSetting(binding.guideSwitchBtn.isChecked)
            viewModel.postPushSetting()
        }

        binding.editBtn.setOnClickListener {
            navController.navigate(R.id.my_fragment_to_my_edit_fragment)
        }
        binding.logoutBtn.setOnClickListener{
            viewModel.deletePreference()
            val intent = Intent(
                context,
                Class.forName("com.charaminstra.pleon.login.ui.LoginActivity")
            )
            startActivity(intent)
            activity?.finish()

            // logging
            val loggingBundle = Bundle()
            loggingBundle.putString(CLASS_NAME, TAG)
            firebaseAnalytics.logEvent(LOGOUT_BTN_CLICK , loggingBundle)
        }
    }

    private fun initObservers(){
        viewModel.userName.observe(viewLifecycleOwner, Observer{
            if(it!=null){
                binding.userName.text = it
            }
        })
        viewModel.urlResponse.observe(viewLifecycleOwner, Observer{
            Glide.with(binding.root)
                .load(it)
                .into(binding.userImg)
        })
        viewModel.commentPushSetting.observe(viewLifecycleOwner, Observer {
            binding.commentSwitchBtn.isChecked = it
        })
        viewModel.guidePushSetting.observe(viewLifecycleOwner, Observer {
            binding.guideSwitchBtn.isChecked = it
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.getUserData()
    }
}








