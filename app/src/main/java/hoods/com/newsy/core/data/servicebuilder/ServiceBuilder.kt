package hoods.com.newsy.core.data.servicebuilder

import retrofit2.Retrofit

class ServiceBuilder(private val retrofit: Retrofit) {
    fun <T> buildService(serviceType: Class<T>): T {
        return retrofit.create(serviceType)
    }

}
