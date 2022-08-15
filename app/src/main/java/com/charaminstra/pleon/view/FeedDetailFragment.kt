package com.charaminstra.pleon.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.charaminstra.pleon.R
import com.charaminstra.pleon.adapter.ActionType
import com.charaminstra.pleon.adapter.CommentsAdapter
import com.charaminstra.pleon.databinding.FragmentFeedDetailBinding
import com.charaminstra.pleon.viewmodel.FeedDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat

@AndroidEntryPoint
class FeedDetailFragment : Fragment() {

    private val viewModel: FeedDetailViewModel by viewModels()
    //private lateinit var feedId : String
    private lateinit var binding : FragmentFeedDetailBinding
    private lateinit var navController: NavController
    private lateinit var dateFormat: SimpleDateFormat
    private lateinit var commentsAdapter: CommentsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeedDetailBinding.inflate(layoutInflater)
        arguments?.getString("id")?.let {
            viewModel.feedId = it
        }
        binding.backBtn.setOnClickListener {
            navController.popBackStack()
        }
        binding.commentEt.showKeyboard()
        binding.commentEt.isCursorVisible = true
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dateFormat = SimpleDateFormat(resources.getString(com.charaminstra.pleon.common_ui.R.string.date_format))

        navController = this.findNavController()


        initList()
        initListeners()
        observeViewModel()
        binding.commentsRecyclerview.adapter = commentsAdapter

        binding.moreBtn.setOnClickListener{
            val pop=PopupMenu(requireContext(),it)
            pop.menuInflater.inflate(R.menu.more_menu, pop.menu)
            pop.setOnMenuItemClickListener { item ->
                when(item.itemId){
                    //R.id.item_more_edit -> {
                        //feed 수정
                    //}
                    R.id.item_more_delete -> {
                        //feed 삭제
                        viewModel.deleteFeed()
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
        }
    }

    private fun observeViewModel() {
        viewModel.feedData.observe(viewLifecycleOwner, Observer {
            binding.feedContent.text = it.content
            binding.plantTagTv.text = resources.getString(R.string.plant_tag) + it.plant.name
            for(i in ActionType.values()){
                if(i.action == it.kind){
                    binding.actionTagTv.text = binding.root.context.resources.getString(R.string.action_tag)+i.name
                }
            }
            if (it.image_url == null) {
                binding.plantImage.visibility = View.GONE
            } else {
                binding.plantImage.visibility = View.VISIBLE
                Glide.with(binding.root)
                    .load(it.image_url)
                    .into(binding.plantImage)
            }
            binding.feedDate.text = dateFormat.format(it.publish_date)
            //user data
            binding.userName.text = it.user.nickname
            Glide.with(binding.root).load(it.user.thumbnail).into(binding.userImage)
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

    private fun View.showKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }
}