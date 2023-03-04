package com.charaminstra.pleon.common.repository

import com.charaminstra.pleon.foundation.model.ImageUrlResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import java.io.InputStream
import javax.inject.Inject

class ImageRepository @Inject constructor(private val service: com.charaminstra.pleon.common.api.ImageAPIService) {
    suspend fun postImage(stream: InputStream): Response<ImageUrlResponse> {
        val body = stream.readBytes().toRequestBody("image/*".toMediaTypeOrNull())
        val part = MultipartBody.Part.createFormData(
            "image", "image.jpg", body)
        return service.postImage(part)
    }
}


//class ImageRepository @Inject constructor(private val service: ImageAPIService) {
//    private val TAG = javaClass.simpleName
//    suspend fun postImage(uri: Uri, absolutePath: String): Response<ImageUrlResponse> {
//        val file = File(absolutePath)
//        val fileBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), uri.toString())
//        Log.i(TAG,"filebody : "+fileBody.contentType())
//        val multipartBody = MultipartBody.Part.createFormData("image",file.name, fileBody)
//        Log.i(TAG,"multipartBody.headers : "+multipartBody.headers)
//        Log.i(TAG,"multipartBody.body : "+multipartBody.body)
//
//        return service.postImage(multipartBody)
//    }
//}


