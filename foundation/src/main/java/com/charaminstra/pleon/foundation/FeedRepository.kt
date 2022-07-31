package com.charaminstra.pleon.foundation

import com.charaminstra.pleon.foundation.api.FeedAPIService
import com.charaminstra.pleon.foundation.api.PleonPreference
import com.charaminstra.pleon.foundation.model.FeedRequestBody
import com.charaminstra.pleon.foundation.model.FeedResponse
import javax.inject.Inject

class FeedRepository @Inject constructor(private val service: FeedAPIService, private val prefs: PleonPreference){
//    suspend fun postFeed() = service.postFeed(prefs.getAccessToken(),
//    FeedRequestBody()
//    )
}