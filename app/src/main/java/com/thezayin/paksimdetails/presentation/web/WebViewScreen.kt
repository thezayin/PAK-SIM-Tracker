package com.thezayin.paksimdetails.presentation.web

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Build
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.thezayin.framework.lifecycles.ComposableLifecycle
import com.thezayin.framework.nativead.GoogleNativeAd
import com.thezayin.framework.nativead.GoogleNativeAdStyle
import com.thezayin.paksimdetails.R
import com.thezayin.paksimdetails.presentation.activities.MainViewModel
import com.thezayin.paksimdetails.presentation.activities.dialogs.LoadingDialog
import com.thezayin.paksimdetails.presentation.activities.dialogs.NetworkDialog
import com.thezayin.paksimdetails.presentation.activities.dialogs.NetworkErrorDialog
import com.thezayin.paksimdetails.presentation.servers.presentation.ServerViewModel
import com.thezayin.paksimdetails.presentation.splash.drawGlass
import com.thezayin.paksimdetails.presentation.web.component.WebAppInterface
import com.thezayin.paksimdetails.presentation.web.component.WebScreenTopBar
import com.thezayin.paksimdetails.presentation.web.component.removeElement
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import timber.log.Timber
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStreamReader

@SuppressLint("SetJavaScriptEnabled", "OpaqueUnitKey")
@RequiresApi(Build.VERSION_CODES.O)
@Destination
@Composable
fun WebViewScreen(
    navigator: DestinationsNavigator,
    pageUrl: String
) {
    val context = LocalContext.current
    val viewModel: ServerViewModel = koinInject()
    val mainViewModel: MainViewModel = koinInject()
    var checkNetwork by remember { mutableStateOf(false) }
    var checkBlocked by remember { mutableStateOf(false) }
    var backEnabled by remember { mutableStateOf(false) }
    var webView: WebView? = null
    val infoDialog = remember { mutableStateOf(false) }
    val isLoading = viewModel.loading.collectAsState().value.isLoading.value
    val scope = rememberCoroutineScope()
    val nativeAd = remember { mainViewModel.nativeAd }

    val adServers = StringBuilder()
    var line: String?
    val inputStream = context.resources.openRawResource(com.thezayin.core.R.raw.adblockserverlist)
    val br = BufferedReader(InputStreamReader(inputStream))
    try {
        while (br.readLine().also { line = it } != null) {
            adServers.append(line)
            adServers.append("\n")
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    Image(
        painter = rememberAsyncImagePainter(
            ImageRequest.Builder(context).data(data = R.drawable.bg_vid).apply(block = {
                size(Size.ORIGINAL)
            }).build(), imageLoader = imageLoader
        ),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize(),
    )


    ComposableLifecycle { _, event ->
        when (event) {
            Lifecycle.Event.ON_START -> {
                scope.launch {
                    while (this.isActive) {
                        mainViewModel.getNativeAd()
                        Timber.d("Native_Ad", "getNativeAd:${nativeAd} ")
                        delay(20000L)
                    }
                }
            }

            else -> {
                Timber.tag("Native_Ad").d("Not Called")
            }
        }
    }

    if (checkNetwork) {
        NetworkDialog(showDialog = { checkNetwork = it })
    }

    if (isLoading) {
        LoadingDialog(
            viewModel = mainViewModel,
            showAd = mainViewModel.remoteConfig.showAdOnWebScreenLoadingDialog
        )
    }
    if (checkBlocked) {
        NetworkErrorDialog(showDialog = { checkBlocked = it }, navigator = navigator)
    }

    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding(),
        containerColor = colorResource(id = R.color.semi_transparent),
        topBar = {
            WebScreenTopBar(
                navigator = navigator,
                modifier = Modifier,
                mainViewModel = mainViewModel
            )
        },
        bottomBar = {
            if (mainViewModel.remoteConfig.showAdOnWebScreenNative) {
                GoogleNativeAd(
                    nativeAd = nativeAd.value,
                    style = GoogleNativeAdStyle.Small,
                    modifier = Modifier.navigationBarsPadding()
                )
            }
        }
    ) { paddingValues ->
        AndroidView(
            modifier = Modifier
                .padding(top = 40.dp)
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
                            viewModel.webPageLoading(false)
                            removeElement(view!!)
                        }

                        override fun onPageStarted(view: WebView, url: String?, favicon: Bitmap?) {
                            viewModel.webPageLoading(true)
                            backEnabled = view.canGoBack()
                        }

                        override fun onReceivedError(
                            view: WebView?,
                            request: WebResourceRequest?,
                            error: WebResourceError?
                        ) {
                            super.onReceivedError(view, request, error)
                            viewModel.webPageLoading(false)
                        }
                    }
                    loadUrl(pageUrl)
                    webView = this
                }
            },
            update = {
                webView = it
            }
        )

        BackHandler(enabled = backEnabled) {
            removeElement(webView!!)
            webView?.goBack()
        }
    }
}