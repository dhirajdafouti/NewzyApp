package hoods.com.newsy.core.data.inteceptors

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import hoods.com.newsy.core.domain.NoConnectionException
import hoods.com.newsy.utils.Constants.NO_CONNECTION_EXCEPTION
import okhttp3.Interceptor
import okhttp3.Response

class ConnectionInterceptor(context: Context) : Interceptor {
    companion object {
        val TAG = ConnectionInterceptor::class.simpleName
    }

    private val connectivityManager = context.getSystemService(ConnectivityManager::class.java)

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isConnected()) {
            throw NoConnectionException(NO_CONNECTION_EXCEPTION)
        }
        return chain.proceed(
            chain.request(),
        )
    }


    private fun isConnected(): Boolean {
        val currentNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(currentNetwork)
        val hasConnection = networkCapabilities?.hasCapability(NET_CAPABILITY_INTERNET)
        return hasConnection != null && hasConnection
    }

}
