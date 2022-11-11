package com.charaminstra.pleon.common_ui

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import com.charaminstra.pleon.common_ui.databinding.DialogNotiLayoutBinding

class PLeonNotiDialog(val context: Context){
    private val dlg = Dialog(context)   //부모 액티비티의 context 가 들어감
    private lateinit var goBtnListener : DialogGoBtnClickedListener
    private lateinit var todayStopListener : DialogTodayStopClickedListener

    fun start(title: String, content: String, button: Boolean) {
        val binding = DialogNotiLayoutBinding.inflate(LayoutInflater.from(context))
        if(button){
            binding.notiGoBtn.visibility = View.VISIBLE
        }else{
            binding.notiGoBtn.visibility = View.GONE
        }
        binding.notiDialogTitle.text = title
        binding.notiDialogDesc.text = content
        dlg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) // background round 적용

        dlg.setContentView(binding.root)     //다이얼로그에 사용할 xml 파일을 불러옴

        binding.notiGoBtn.setOnClickListener{
            goBtnListener.onGoBtnClicked()
            dlg.dismiss()
        }
        binding.notiDialogTodayStopBtn.setOnClickListener {
            todayStopListener.onTodayStopClicked()
            dlg.dismiss()
        }

        binding.notiDialogCloseBtn.setOnClickListener {
            dlg.dismiss()
        }

        dlg.show()
    }

    fun setOnGoBtnClickedListener(listener: () -> Unit) {
        this.goBtnListener = object: DialogGoBtnClickedListener {
            override fun onGoBtnClicked() {
                listener()
            }
        }
    }
    interface DialogGoBtnClickedListener {
        fun onGoBtnClicked()
    }

    fun setOnTodayStopClickedListener(listener: () -> Unit) {
        this.todayStopListener = object: DialogTodayStopClickedListener {
            override fun onTodayStopClicked() {
                listener()
            }
        }
    }
    interface DialogTodayStopClickedListener {
        fun onTodayStopClicked()
    }
}
