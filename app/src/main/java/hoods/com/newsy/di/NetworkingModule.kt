package hoods.com.newsy.di

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hoods.com.newsy.BuildConfig
import hoods.com.newsy.core.data.inteceptors.ConnectionInterceptor
import hoods.com.newsy.core.data.inteceptors.HeaderInterceptor
import hoods.com.newsy.core.data.inteceptors.LoggingInterceptor
import hoods.com.newsy.core.data.servicebuilder.ServiceBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Instant

import javax.inject.Qualifier
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkingModule {
    private val baseUrl = BuildConfig.BASE_URL

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class DefaultOkHttpClient

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class DefaultRetrofit

    @Provides
    @Singleton
    fun provideConnectionInterceptor(
        @ApplicationContext applicationContext: Context,
    ) = ConnectionInterceptor(applicationContext)

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): LoggingInterceptor = LoggingInterceptor()

    @Provides
    @Singleton
    fun provideHeaderInterceptor(

    ): HeaderInterceptor =
        HeaderInterceptor()


    @RequiresApi(Build.VERSION_CODES.O)
    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().registerTypeAdapter(
        Instant::class.java,
        JsonDeserializer { json, _, _ ->
            Instant.parse(json.asString)
        },
    ).create()

    @Provides
    @Singleton
    fun provideGsonConverterFactory(
        gson: Gson,
    ): GsonConverterFactory = GsonConverterFactory.create(gson)

    @DefaultOkHttpClient
    @Provides
    fun provideDefaultOkHttpClient(
        connectionInterceptor: ConnectionInterceptor,
        headerInterceptor: HeaderInterceptor,
        loggingInterceptor: LoggingInterceptor,
    ): OkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(connectionInterceptor)
        .addInterceptor(headerInterceptor)
        .addInterceptor(loggingInterceptor)
        .build()

    @DefaultRetrofit
    @Provides
    fun provideRetrofit(
        @DefaultOkHttpClient okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
    ): Retrofit = Retrofit
        .Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(gsonConverterFactory)
        .build()

    @Provides
    @Singleton
    fun provideServiceBuilder(
        @DefaultRetrofit retrofit: Retrofit,
    ) = ServiceBuilder(retrofit)


}
