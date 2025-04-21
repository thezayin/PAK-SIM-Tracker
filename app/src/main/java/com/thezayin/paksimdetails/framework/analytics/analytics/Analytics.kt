package com.thezayin.paksimdetails.framework.analytics.analytics

import com.thezayin.paksimdetails.framework.analytics.events.AnalyticsEvent

interface Analytics {
    fun logEvent(event: AnalyticsEvent)
}