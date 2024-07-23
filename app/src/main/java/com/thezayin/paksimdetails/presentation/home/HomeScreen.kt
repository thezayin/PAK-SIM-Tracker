package com.thezayin.paksimdetails.presentation.home

import android.annotation.SuppressLint
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.thezayin.framework.lifecycles.ComposableLifecycle
import com.thezayin.paksimdetails.R
import com.thezayin.paksimdetails.presentation.activities.MainActivity
import com.thezayin.paksimdetails.presentation.activities.MainViewModel
import com.thezayin.paksimdetails.presentation.activities.dialogs.LoadingDialog
import com.thezayin.paksimdetails.presentation.activities.dialogs.NetworkDialog
import com.thezayin.paksimdetails.presentation.destinations.ResultScreenDestination
import com.thezayin.paksimdetails.presentation.home.component.GetAllServices
import com.thezayin.paksimdetails.presentation.home.component.HomeBottomBar
import com.thezayin.paksimdetails.presentation.home.component.SearchCardCom
import com.thezayin.paksimdetails.presentation.home.component.TopBarComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import timber.log.Timber

@SuppressLint("OpaqueUnitKey")
@Destination
@Composable
fun HomeScreen(navigator: DestinationsNavigator) {
    val viewModel: HomeViewModel = koinInject()
    val mainViewModel: MainViewModel = koinInject()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val nativeAd = remember { mainViewModel.nativeAd }
    var checkNetwork by remember { mutableStateOf(false) }
    val isLoading = viewModel.isLoading.collectAsState().value.isLoading.value
    val activity = LocalContext.current as MainActivity

    if (checkNetwork) {
        NetworkDialog(showDialog = { checkNetwork = it })
    }

    if (isLoading) {
        LoadingDialog(
            mainViewModel,
            showAd = mainViewModel.remoteConfig.showAdOnHomeScreenLoadingDialog
        )
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

    val simDetailsModel = viewModel.simDetails.collectAsState().value.data.value
    val isResultSuccessful = viewModel.resultSuccess.collectAsState().value.isSuccessful.value
    LaunchedEffect(isResultSuccessful) {
        scope.launch(Dispatchers.Main) {
            if (isResultSuccessful) {
                navigator.navigate(ResultScreenDestination(simDetailsModel))
            }
        }
    }

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

    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding(),
        containerColor = colorResource(id = R.color.semi_transparent),

        topBar = {
            TopBarComponent(
                modifier = Modifier,
                navigator = navigator,
                mainViewModel = mainViewModel
            )
        },

        bottomBar = {
            HomeBottomBar(
                modifier = Modifier.navigationBarsPadding(),
                navigator = navigator,
                nativeAd = nativeAd.value,
                mainViewModel = mainViewModel
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SearchCardCom(
                    modifier = Modifier
                        .padding(25.dp),
                    navigator = navigator
                )
                GetAllServices(
                    mainViewModel = mainViewModel,
                    modifier = Modifier,
                    navigator = navigator
                )
            }

        }
    }

    BackHandler {
        activity.finish()
    }
}