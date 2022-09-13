package com.charaminstra.pleon.common_ui

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.charaminstra.pleon.common_ui.databinding.ImageMenuLayoutBinding

class PopUpImageMenu(val context: Context){
    private val dlg = Dialog(context)   //부모 액티비티의 context 가 들어감
    private lateinit var cameraListener : DialogCameraClickedListener
    private lateinit var galleryListener : DialogGalleryClickedListener

    fun start() {
        val binding = ImageMenuLayoutBinding.inflate(LayoutInflater.from(context))
        dlg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) // background round 적용
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거

       // dlg.window?.setLayout(binding.imageMenuRoot.width,binding.imageMenuRoot.height)
        dlg.setContentView(binding.root)     //다이얼로그에 사용할 xml 파일을 불러옴



        binding.cancelBtn.setOnClickListener {
            it.setBackgroundColor(Color.BLACK)
            dlg.dismiss()
        }

        binding.cameraBtn.setOnClickListener{
            cameraListener.onCameraClicked()
            dlg.dismiss()
        }

        binding.galleryBtn.setOnClickListener{
            galleryListener.onGalleryClicked()
            dlg.dismiss()
        }

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
