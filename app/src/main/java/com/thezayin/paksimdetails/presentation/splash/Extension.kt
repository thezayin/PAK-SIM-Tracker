package com.thezayin.paksimdetails.presentation.splash

import android.content.Context
import android.graphics.BlurMaskFilter
import android.net.Uri
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas

fun Context.getVideoUri(): Uri {
    val rawId = resources.getIdentifier("video_first", "raw", packageName)
    return Uri.parse("android.resource://$packageName/$rawId")
}
fun DrawScope.drawGlass(center: Offset, radius: Float, position: Offset) {
    val glassCenter = center + position
    val glassShader = LinearGradientShader(
        from = glassCenter + Offset(-radius, -radius),
        to = glassCenter + Offset(radius, radius),
        colors = listOf(
            Color.White.copy(alpha = 0.1f),
            Color.White.copy(alpha = 0.4f),
            Color.White.copy(alpha = 0.4f),
            Color.White.copy(alpha = 0.3f),
            Color.White.copy(alpha = 0.1f)
        ),
        colorStops = listOf(0f, 0.2f, 0.5f, 0.8f, 1f)
    )


    // Draw blurred circle
    drawIntoCanvas { canvas ->
        val paint = Paint()
        paint.asFrameworkPaint().maskFilter = BlurMaskFilter(7f, BlurMaskFilter.Blur.INNER)
        canvas.drawCircle(glassCenter, radius, paint.apply {
            shader = glassShader
        })
    }
}