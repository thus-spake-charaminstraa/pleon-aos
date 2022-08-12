package com.charaminstra.pleon.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charaminstra.pleon.foundation.CommentRepository
import com.charaminstra.pleon.foundation.FeedRepository
import com.charaminstra.pleon.foundation.model.CommentObject
import com.charaminstra.pleon.foundation.model.FeedObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedDetailViewModel @Inject constructor(
    private val feedRepository: FeedRepository,
    private val commentRepository: CommentRepository
): ViewModel() {
    private val TAG = javaClass.name

    private val _feedData = MutableLiveData<FeedObject>()
    val feedData : LiveData<FeedObject> = _feedData

    private val _feedComments = MutableLiveData<List<CommentObject>>()
    val feedComments : LiveData<List<CommentObject>> = _feedComments

    private val _feedDeleteSuccess = MutableLiveData<Boolean>()
    val feedDeleteSuccess : LiveData<Boolean> = _feedDeleteSuccess

    private val _postCommentSuccess = MutableLiveData<Boolean>()
    val postCommentSuccess : LiveData<Boolean> = _postCommentSuccess

    var feedId: String? = null


    fun loadFeed(){
        viewModelScope.launch {
            val data = feedRepository.getFeedId(feedId!!)
            when (data.isSuccessful) {
                true -> {
                    _feedData.postValue(data.body()?.data!!)
                    //_feedComments.postValue(data.body()?.data?.comments!!)
                    Log.i(TAG,"SUCCESS -> "+ data.body().toString())
                }
                else -> {
                    Log.i(TAG,"FAIL -> "+ data.body().toString())
                }
            }
        }
    }

    fun getCommentList(){
        viewModelScope.launch {
            val data = commentRepository.getComment(feedId!!)
            when (data.isSuccessful) {
                true -> {
                    _feedComments.postValue(data.body()?.data!!)
                    Log.i(TAG,"SUCCESS -> "+ data.body().toString())
                }
                else -> {
                    Log.i(TAG,"FAIL -> "+ data.body().toString())
                }
            }
        }
    }

    fun postComment(content: String){
        viewModelScope.launch {
            val data = commentRepository.postComment(feedId!!, content)
            when (data.isSuccessful) {
                true -> {
                    Log.i(TAG,"SUCCESS post Comment \n"+ data.body().toString())
                    _postCommentSuccess.postValue(data.body()?.success!!)
                }
                else -> {
                    Log.i(TAG,"FAIL post Comment \n"+ data.body().toString())
                }
            }
        }
    }


    fun deleteFeed(){
        viewModelScope.launch {
            val data = feedRepository.deleteFeedId(feedId!!)
            when (data.isSuccessful) {
                true -> {
                    _feedDeleteSuccess.postValue(data.body()?.success!!)
                    Log.i(TAG,"SUCCESS -> "+ data.body().toString())
                }
                else -> {
                    Log.i(TAG,"FAIL -> "+ data.body().toString())
                }
            }
        }
    }
}