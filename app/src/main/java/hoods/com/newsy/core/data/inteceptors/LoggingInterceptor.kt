package hoods.com.newsy.core.data.inteceptors

import android.net.Uri
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody

class LoggingInterceptor : Interceptor {
    companion object {
        val TAG = LoggingInterceptor::class.simpleName
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response: Response = chain.proceed(request)
        val responseBody: ResponseBody? = response.body
        val responseBodyString = response.body!!.string()


        // return new response (extracting the body for logging consumes it)
        return response.newBuilder()
            .body(responseBodyString.toResponseBody(responseBody?.contentType()))
            .build()
    }
}
