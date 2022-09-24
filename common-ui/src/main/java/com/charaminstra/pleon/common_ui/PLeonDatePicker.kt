package com.charaminstra.pleon.common_ui

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.charaminstra.pleon.common_ui.databinding.DialogDatepickerBinding

class PLeonDatePicker(val context: Context) {
    private val dlg = Dialog(context)   //부모 액티비티의 context 가 들어감

    private lateinit var dialogTitle : TextView
    private lateinit var btnOK : Button
    private lateinit var btnCancel : Button
    private lateinit var okListener : DialogOkClickedListener

    fun start(title: String) {
        val binding = DialogDatepickerBinding.inflate(LayoutInflater.from(context))
        dlg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) // background round 적용
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거
        dlg.setContentView(binding.root)     //다이얼로그에 사용할 xml 파일을 불러옴

        dialogTitle = binding.plantAdoptDatePickerTitle
        dialogTitle.text= title

        btnOK = binding.plantAdoptDatePickerOkBtn
        btnOK.text = "확인"
        btnOK.setOnClickListener {
            okListener.onOKClicked()
            dlg.dismiss()
        }

        btnCancel = binding.plantAdoptDatePickerCancelBtn
        btnCancel.text = "취소"
        btnCancel.setOnClickListener {
            dlg.dismiss()
        }

        dlg.show()
    }

    fun setOnOKClickedListener(listener: () -> Unit) {
        this.okListener = object: DialogOkClickedListener {
            override fun onOKClicked() {
                listener()
            }
        }
    }
    interface DialogOkClickedListener {
        fun onOKClicked()
    }
}