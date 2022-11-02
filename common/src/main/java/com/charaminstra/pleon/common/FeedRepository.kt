package com.charaminstra.pleon.common

import com.charaminstra.pleon.foundation.api.PleonPreference
import com.charaminstra.pleon.foundation.model.FeedRequestBody
import javax.inject.Inject

class FeedRepository @Inject constructor(private val service: FeedAPIService, private val prefs: PleonPreference){
    suspend fun postFeed(plantId : String, date: String, kind: String, content: String, url: String?) = service.postFeed(
        prefs.getAccessToken(),
        FeedRequestBody(plantId, date, kind, content, url))

    suspend fun getFeed(offset: Int?, plantId: String?, date: String?) = service.getFeedList(
        prefs.getAccessToken(),
        10,
        offset,
        plantId,
        date)

    suspend fun getFeedId(feedId:String) = service.getFeedId(
        prefs.getAccessToken(),
        feedId)

    suspend fun deleteFeedId(feedId: String) = service.deleteFeedId(
        prefs.getAccessToken(),
        feedId
    )

    suspend fun getAction() = service.getAction()
}

