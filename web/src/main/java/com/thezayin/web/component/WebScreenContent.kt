@file:Suppress("DEPRECATION")

package com.thezayin.web.component

import android.graphics.Bitmap
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.nativead.NativeAd
import com.thezayin.common.topbar.TopBar
import com.thezayin.framework.nativead.GoogleNativeAd
import com.thezayin.framework.nativead.GoogleNativeAdStyle
import ir.kaaveh.sdpcompose.sdp
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStreamReader

@Composable
fun WebScreenContent(
    modifier: Modifier,
    url: String,
    backEnabled: MutableState<Boolean>,
    infoDialog: MutableState<Boolean>,
    showLoading: () -> Unit,
    hideLoading: () -> Unit,
    onBackClick: () -> Unit,
    onPremiumClick: () -> Unit,
    nativeAd: MutableState<NativeAd?>,
    showBottomAd: Boolean?,
) {
    val context = LocalContext.current
    val adServers = StringBuilder()
    var line: String?
    val inputStream = context.resources.openRawResource(com.thezayin.values.R.raw.adblockserverlist)
    val br = BufferedReader(InputStreamReader(inputStream))
    var webView: WebView? = null

    try {
        while (br.readLine().also { line = it } != null) {
            adServers.append(line)
            adServers.append("\n")
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
        containerColor = colorResource(id = com.thezayin.values.R.color.background),
        topBar = {
            TopBar(
                modifier = Modifier,
                onBackClick = onBackClick,
                onPremiumClick = onPremiumClick,
                screenName = "Server"
            )
        },
        bottomBar = {
            if (showBottomAd == true) {
                GoogleNativeAd(
                    modifier = Modifier,
                    style = GoogleNativeAdStyle.Small,
                    nativeAd = nativeAd.value
                )
            }
        }
    ) { paddingValues ->
        AndroidView(
            modifier = Modifier
                .padding(top = 20.sdp)
                .padding(paddingValues)
                .fillMaxSize(),
            factory = { context ->
                WebView(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    webViewClient = WebViewClient()
                    settings.javaScriptEnabled = true
                    /**
                     * to verify that the client requesting your web page is actually your Android app.
                     */
                    settings.userAgentString = System.getProperty("http.agent")
                    settings.useWideViewPort = true

                    /**
                     * Bind JavaScript code to Android code
                     */
                    addJavascriptInterface(WebAppInterface(context, infoDialog), "Android")
                    settings.setSupportZoom(false)
                    webViewClient = object : WebViewClient() {
                        override fun onReceivedError(
                            view: WebView?,
                            errorCode: Int,
                            description: String?,
                            failingUrl: String?
                        ) {
                            super.onReceivedError(view, errorCode, description, failingUrl)
                        }

                        override fun shouldInterceptRequest(
                            view: WebView,
                            request: WebResourceRequest
                        ): WebResourceResponse? {
                            val empty = ByteArrayInputStream("".toByteArray())
                            val kk5 = adServers.toString()
                            if (kk5.contains(":::::" + request.url.host))
                                return WebResourceResponse("text/plain", "utf-8", empty)
                            return super.shouldInterceptRequest(view, request)
                        }

                        override fun onPageFinished(view: WebView?, url: String?) {
                            super.onPageFinished(view, url)
                            hideLoading()
                            removeElement(view!!)
                        }

                        override fun onPageStarted(view: WebView, url: String?, favicon: Bitmap?) {
                            showLoading()
                            backEnabled.value = view.canGoBack()
                        }

                        override fun onReceivedError(
                            view: WebView?,
                            request: WebResourceRequest?,
                            error: WebResourceError?
                        ) {
                            super.onReceivedError(view, request, error)
                            hideLoading()
                        }
                    }
                    loadUrl(url)
                    webView = this
                }
            },
            update = {
                webView = it
            }
        )

        BackHandler(enabled = backEnabled.value) {
            removeElement(webView!!)
            webView?.goBack()
        }
    }
}