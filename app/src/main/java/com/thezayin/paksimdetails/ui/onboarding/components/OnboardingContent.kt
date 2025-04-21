package com.thezayin.paksimdetails.ui.onboarding.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.thezayin.paksimdetails.R
import com.thezayin.paksimdetails.ui.common.component.BannerAd
import com.thezayin.paksimdetails.ui.onboarding.model.OnboardingPage
import ir.kaaveh.sdpcompose.sdp

@Composable
fun OnboardingContent(
    currentPage: Int,
    onboardPages: List<OnboardingPage>,
    onNextClicked: () -> Unit
) {
    Scaffold(
        modifier = Modifier.navigationBarsPadding(),
        containerColor = colorResource(R.color.white),
        bottomBar = {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.sdp)
                        .padding(start = 25.sdp, end = 10.sdp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    PageIndicator(
                        totalPages = onboardPages.size,
                        currentPage = currentPage,
                        modifier = Modifier
                    )
                    OnBoardNavButton(
                        modifier = Modifier.padding(top = 10.sdp, bottom = 5.sdp),
                    ) {
                        onNextClicked()
                    }
                }
                BannerAd()
            }
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OnBoardImageView(
                modifier = Modifier
                    .fillMaxSize()
                    .navigationBarsPadding(),
                image = onboardPages[currentPage].images,
            )
        }
    }
}
