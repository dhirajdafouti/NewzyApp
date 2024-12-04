package hoods.com.newsy

import android.app.Activity
import android.app.Application
import android.os.Handler
import android.os.Looper
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import hoods.com.newsy.utils.ActivityLifecycleCallbacks


open class NewzyApplication : Application(), ExceptionListener {
    private val activeActivityCallbacks = ActivityLifecycleCallbacks()

    private fun getCurrentActivity(): Activity? = activeActivityCallbacks.getCurrentActivity()

    companion object {
        lateinit var instance: NewzyApplication
            private set

        fun isInstanceInitialized() = ::instance.isInitialized
    }

    override fun onCreate() {
        super<Application>.onCreate()
        setupExceptionHandler()
        instance = this
        registerActivityLifecycleCallbacks(activeActivityCallbacks)
    }

    private fun setupExceptionHandler() {
        Handler(Looper.getMainLooper()).post {
            while (true) {
                try {
                    Looper.loop()
                } catch (e: Throwable) {
                    unCaughtException(Looper.getMainLooper().thread, e)
                }
            }
        }
        Thread.setDefaultUncaughtExceptionHandler { thread: Thread, throwable: Throwable ->
            unCaughtException(thread, throwable)
        }
    }

    override fun unCaughtException(thread: Thread, throwable: Throwable) {
        try {
            val message = throwable.message ?: "Something went wrong"

            val activity = getCurrentActivity()
            activity?.runOnUiThread {
                val alert = MaterialAlertDialogBuilder(activity)
                alert.setCancelable(true)
                alert.setTitle("Unknown Error")
                alert.setMessage(message)
                alert.setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }
                alert.show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        unregisterActivityLifecycleCallbacks(activeActivityCallbacks)

    }
}


interface ExceptionListener {
    fun unCaughtException(thread: Thread, throwable: Throwable)
}
