package hoods.com.newsy.core.data.inteceptors

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.os.Build
import androidx.annotation.RequiresApi
import hoods.com.newsy.core.domain.util.NoConnectionException
import hoods.com.newsy.utils.Constants.NO_CONNECTION_EXCEPTION
import okhttp3.Interceptor
import okhttp3.Response

class ConnectionInterceptor(context: Context) : Interceptor {
    companion object {
        val TAG = ConnectionInterceptor::class.simpleName
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private val connectivityManager = context.getSystemService(ConnectivityManager::class.java)

    @RequiresApi(Build.VERSION_CODES.M)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isConnected()) {
            throw NoConnectionException(NO_CONNECTION_EXCEPTION)
        }
        return chain.proceed(
            chain.request(),
        )
    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun isConnected(): Boolean {
        val currentNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(currentNetwork)
        val hasConnection = networkCapabilities?.hasCapability(NET_CAPABILITY_INTERNET)
        return hasConnection != null && hasConnection
    }

}
