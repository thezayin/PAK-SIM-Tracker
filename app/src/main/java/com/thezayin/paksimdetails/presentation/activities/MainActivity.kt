package com.thezayin.paksimdetails.presentation.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ramcosta.composedestinations.DestinationsNavHost
import com.thezayin.framework.extension.ads.showAppOpenAd
import com.thezayin.paksimdetails.presentation.NavGraphs
import com.thezayin.paksimdetails.ui.theme.PakSimDetailsTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.googleManager.init(this)
        viewModel.googleManager.initOnLastConsent()
        setContent {
            PakSimDetailsTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        showAppOpenAd(
            activity = this,
            googleManager = viewModel.googleManager
        )
    }
}
