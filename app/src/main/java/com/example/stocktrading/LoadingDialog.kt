package com.example.stocktrading

import android.app.Activity
import android.app.AlertDialog

class LoadingDialog(val mActivity: Activity) {
    private lateinit var isdialog: AlertDialog
    fun startLoading(){
        val infalter=mActivity.layoutInflater
        val dialogView=infalter.inflate(R.layout.loading_activity,null)

        val builder= AlertDialog.Builder(mActivity)
        builder.setView(dialogView)
        builder.setCancelable(false)
        isdialog=builder.create()
        isdialog.show()
    }
    fun isDismiss(){
        isdialog.dismiss()
    }
}