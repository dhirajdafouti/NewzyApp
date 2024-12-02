package hoods.com.newsy.utils

import android.app.Activity
import android.app.Application
import android.os.Bundle

class ActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {

    private var currentActivity: Activity? = null
    fun getCurrentActivity(): Activity? = currentActivity
    override fun onActivityCreated(p0: Activity, p1: Bundle?) {
        currentActivity = p0
    }

    override fun onActivityStarted(p0: Activity) {
        currentActivity = p0
    }

    override fun onActivityResumed(p0: Activity) {
        currentActivity = p0
    }

    override fun onActivityPaused(p0: Activity) {
        currentActivity = p0
    }

    override fun onActivityStopped(p0: Activity) {
        currentActivity = p0
    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
        currentActivity = p0
    }

    override fun onActivityDestroyed(p0: Activity) {
        currentActivity = p0
    }
}
