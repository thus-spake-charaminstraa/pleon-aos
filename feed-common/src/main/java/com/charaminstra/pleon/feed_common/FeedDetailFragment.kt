package com.charaminstra.pleon.feed_common

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.charaminstra.pleon.common.CLASS_NAME
import com.charaminstra.pleon.common.COMMENT_WRITE_COMPLETE_BTN_CLICK
import com.charaminstra.pleon.common.FEED_DETAIL_VIEW
import com.charaminstra.pleon.common_ui.PLeonMsgDialog
import com.charaminstra.pleon.feed_common.databinding.FragmentFeedDetailBinding
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedDetailFragment : Fragment() {
    private val TAG = javaClass.name

    private val viewModel: FeedDetailViewModel by viewModels()
    private lateinit var binding : FragmentFeedDetailBinding
    private lateinit var navController: NavController
    private lateinit var commentsAdapter: CommentsAdapter

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAnalytics= FirebaseAnalytics.getInstance(requireContext())

        // logging
        val bundle = Bundle()
        bundle.putString(CLASS_NAME, TAG)
        firebaseAnalytics.logEvent(FEED_DETAIL_VIEW, bundle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeedDetailBinding.inflate(layoutInflater).also {
        }

        binding.feedDetailBackBtn.setOnClickListener {
            navController.popBackStack()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = this.findNavController()

        initList()
        initListeners()
        observeViewModel()
        binding.commentsRecyclerview.adapter = commentsAdapter

        binding.moreBtn.setOnClickListener{
            val pop= PopupMenu(requireContext(),it)
            pop.menuInflater.inflate(R.menu.more_menu, pop.menu)
            pop.setOnMenuItemClickListener { item ->
                when(item.itemId){
                    //R.id.item_more_edit -> {
                        //feed 수정
                    //}
                    R.id.item_more_delete -> {
                        //feed 삭제
                        val dlg = PLeonMsgDialog(requireContext())
                        dlg.setOnOKClickedListener {
                            viewModel.deleteFeed()
                        }
                        dlg.start(
                            resources.getString(R.string.feed_detail_skip_dialog_title),
                            resources.getString(R.string.feed_detail_skip_dialog_desc),
                            resources.getString(R.string.feed_detail_skip_dialog_cancel_btn),
                            resources.getString(R.string.feed_detail_skip_dialog_delete_btn)
                        )

                    }
                }
                false
            }
            pop.show()
        }
    }
    private fun initList(){
        commentsAdapter = CommentsAdapter()
    }

    private fun initListeners(){
        binding.commentEnterBtn.setOnClickListener {
            viewModel.postComment(binding.commentEt.text.toString())
            // logging
            val bundle = Bundle()
            bundle.putString(CLASS_NAME, TAG)
            firebaseAnalytics.logEvent(COMMENT_WRITE_COMPLETE_BTN_CLICK, bundle)
        }
    }

    private fun observeViewModel() {
        viewModel.feedData.observe(viewLifecycleOwner, Observer {
            binding.apply {
                feed = it
            }
            if (it.image_url == null) {
                binding.plantImage.visibility = View.GONE
            } else {
                binding.plantImage.visibility = View.VISIBLE
            }
        })
        //댓글 수
        viewModel.commentsCount.observe(viewLifecycleOwner, Observer {
            binding.feedCommentCount.text = it.toString()
        })
        viewModel.feedComments.observe(viewLifecycleOwner, Observer {
            commentsAdapter.refreshItems(it)
        })
        viewModel.postCommentSuccess.observe(viewLifecycleOwner, Observer {
            viewModel.getCommentList()
            binding.commentEt.text?.clear()
        })
        viewModel.feedDeleteSuccess.observe(viewLifecycleOwner, Observer {
            if (it) {
                navController.popBackStack()
            } else {
                //삭제 실패
            }
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadFeed()
        viewModel.getCommentList()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}