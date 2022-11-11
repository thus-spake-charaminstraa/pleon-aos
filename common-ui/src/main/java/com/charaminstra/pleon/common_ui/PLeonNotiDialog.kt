package com.charaminstra.pleon.common_ui

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import com.charaminstra.pleon.common_ui.databinding.DialogNotiLayoutBinding
import com.charaminstra.pleon.common_ui.databinding.ImageMenuLayoutBinding

class PLeonNotiDialog(val context: Context){
    private val dlg = Dialog(context)   //부모 액티비티의 context 가 들어감
    private lateinit var cameraListener : DialogCameraClickedListener
    private lateinit var galleryListener : DialogGalleryClickedListener

    fun start(title: String, content: String) {
        val binding = DialogNotiLayoutBinding.inflate(LayoutInflater.from(context))
        binding.notiDialogTitle.text = title
        binding.notiDialogDesc.text = content
        dlg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) // background round 적용
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거

        dlg.setContentView(binding.root)     //다이얼로그에 사용할 xml 파일을 불러옴

//        binding.cameraBtn.setOnClickListener{
//            cameraListener.onCameraClicked()
//            dlg.dismiss()
//        }
//
//        binding.galleryBtn.setOnClickListener{
//            galleryListener.onGalleryClicked()
//            dlg.dismiss()
//        }

        dlg.show()
    }

    fun setOnCameraClickedListener(listener: () -> Unit) {
        this.cameraListener = object: DialogCameraClickedListener {
            override fun onCameraClicked() {
                listener()
            }
        }
    }
    interface DialogCameraClickedListener {
        fun onCameraClicked()
    }

    fun setOnGalleryClickedListener(listener: () -> Unit) {
        this.galleryListener = object: DialogGalleryClickedListener {
            override fun onGalleryClicked() {
                listener()
            }
        }
    }
    interface DialogGalleryClickedListener {
        fun onGalleryClicked()
    }
}
