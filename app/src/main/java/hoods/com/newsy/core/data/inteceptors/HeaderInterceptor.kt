package hoods.com.newsy.core.data.inteceptors

import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor() : Interceptor {
    companion object {
        val TAG = HeaderInterceptor::class.simpleName
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val headers = Headers.Builder().add("content-type", "application/json").build()
        val request = original.newBuilder().headers(headers).method(original.method, original.body).build()
        return chain.proceed(request)
    }
}
