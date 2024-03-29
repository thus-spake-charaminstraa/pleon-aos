package com.charaminstra.pleon.common_ui

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.Button
import android.widget.TextView

class PLeonMsgDialog(context: Context) {
    private val dlg = Dialog(context)   //부모 액티비티의 context 가 들어감
    private lateinit var dialogTitle : TextView
    private lateinit var dialogDesc : TextView
    private lateinit var btnOK : Button
    private lateinit var btnCancel : Button
    private lateinit var okListener : DialogOkClickedListener

    fun start(title: String, desc: String, cancel: String, ok: String) {
        dlg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) // background round 적용
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거
        dlg.setContentView(R.layout.dialog_delete_layout)     //다이얼로그에 사용할 xml 파일을 불러옴

        dialogTitle = dlg.findViewById(R.id.dialog_title)
        dialogTitle.text= title
        dialogDesc = dlg.findViewById(R.id.dialog_desc)
        dialogDesc.text = desc

        btnOK = dlg.findViewById(R.id.plant_water_date_picker_ok_btn)
        btnOK.text = ok
        btnOK.setOnClickListener {
            okListener.onOKClicked()
            dlg.dismiss()
        }

        btnCancel = dlg.findViewById(R.id.plant_water_date_picker_cancel_btn)
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