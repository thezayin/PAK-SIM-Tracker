package com.thezayin.paksimdetails.ui.web.component

import android.content.Context
import android.webkit.JavascriptInterface
import android.widget.Toast
import androidx.compose.runtime.MutableState

/** Instantiate the interface and set the context  */
class WebAppInterface(private val mContext: Context, private var infoDialog: MutableState<Boolean>) {

    /** Show a toast from the web page  */
    @JavascriptInterface
    fun showToast(toast: String) {
        infoDialog.value=true
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show()
    }
}