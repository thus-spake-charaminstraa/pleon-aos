package com.charaminstra.pleon.common_ui

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DatepickerDialog(context: Context) {
    private val dlg = Dialog(context)   //부모 액티비티의 context 가 들어감
    private lateinit var dialogTitle : TextView
    private lateinit var dialogDesc : TextView
    private lateinit var btnOK : Button
    private lateinit var btnCancel : Button
    private lateinit var okListener : DialogOkClickedListener

    fun start(title: String, desc: String, cancel: String, ok: String) {
        dlg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) // background round 적용
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거
        dlg.setContentView(R.layout.dialog_datepicker)     //다이얼로그에 사용할 xml 파일을 불러옴
        dlg.setCancelable(false)    //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함

//        dialogTitle = dlg.findViewById(R.id.dialog_title)
//        dialogTitle.text= title
//        dialogDesc = dlg.findViewById(R.id.dialog_desc)
//        dialogDesc.text = desc

        btnOK = dlg.findViewById(R.id.ok_btn)
        btnOK.text = ok
        btnOK.setOnClickListener {
            okListener.onOKClicked()
            dlg.dismiss()
        }

        btnCancel = dlg.findViewById(R.id.cancel_btn)
        btnCancel.text = cancel
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