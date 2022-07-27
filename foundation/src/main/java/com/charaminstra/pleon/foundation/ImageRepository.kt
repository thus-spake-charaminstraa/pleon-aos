package com.charaminstra.pleon.foundation

import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.charaminstra.pleon.foundation.api.ImageAPIService
import com.charaminstra.pleon.foundation.model.ImageUrlResponse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import java.io.File
import java.io.InputStream
import javax.inject.Inject

class ImageRepository @Inject constructor(private val service: ImageAPIService) {
    suspend fun postImage(stream: InputStream): Response<ImageUrlResponse> {
        val part = MultipartBody.Part.createFormData(
            "image", "image.jpg",
            stream.readBytes()
                .toRequestBody(
                    "image/*".toMediaTypeOrNull())
        )
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


