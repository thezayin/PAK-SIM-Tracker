package com.thezayin.paksimdetails.presentation.splash

import android.annotation.SuppressLint
import android.os.Build.VERSION.SDK_INT
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.thezayin.framework.extension.ads.showAppOpenAd
import com.thezayin.paksimdetails.R
import com.thezayin.paksimdetails.presentation.activities.MainActivity
import com.thezayin.paksimdetails.presentation.activities.MainViewModel
import com.thezayin.paksimdetails.presentation.destinations.HomeScreenDestination
import kotlinx.coroutines.delay
import org.koin.compose.koinInject

@SuppressLint("OpaqueUnitKey")
@Composable
@RootNavGraph(start = true)
@Destination
fun SplashScreen(
    navigator: DestinationsNavigator,
) {
    val mainViewModel: MainViewModel = koinInject()
    val activity = LocalContext.current as MainActivity
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        delay(7000L)
        showAppOpenAd(
            activity = activity,
            googleManager = mainViewModel.googleManager,
            showAd = mainViewModel.remoteConfig.showAdOnAppOpen
        ) {
            navigator.navigate(HomeScreenDestination)
        }
    }

    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
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



    Box(
        modifier = Modifier
            .background(color = colorResource(id = R.color.semi_transparent))
            .fillMaxSize()
    ) {
        Text(
            text = "Pak Sim Data",
            fontSize = 26.sp,
            color = colorResource(id = R.color.black),
            fontFamily = FontFamily(Font(com.thezayin.core.R.font.noto_sans_bold)),
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.Center)
        )

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 25.dp, vertical = 25.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                modifier = Modifier
                    .padding(bottom = 10.dp),
                color = colorResource(id = R.color.black),
                text = "Get details of any sim number in Pakistan",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(com.thezayin.core.R.font.abeezee_regular))
            )
        }
    }
}