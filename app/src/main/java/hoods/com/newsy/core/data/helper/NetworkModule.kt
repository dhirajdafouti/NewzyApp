package hoods.com.newsy.core.data.helper

import com.google.gson.Gson
import hoods.com.newsy.core.domain.util.NetworkError
import hoods.com.newsy.core.domain.util.Result
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import retrofit2.Response
import java.nio.channels.UnresolvedAddressException
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.coroutineContext

@Singleton
class NetworkModule @Inject constructor(val gson: Gson) {

    companion object {
        val TAG = NetworkModule::class.simpleName
        const val MAX_ATTEMPTS = 3
    }

    suspend inline fun <T> safeApiCall(
        apiCall: () -> Response<T>,
    ): Result<T, NetworkError> {
        val response = try {
            apiCall()
        } catch (e: UnresolvedAddressException) {
            return Result.Error(NetworkError.NO_INTERNET)
        } catch (e: SerializationException) {
            return Result.Error(NetworkError.SERIALIZATION)
        } catch (e: Exception) {
            coroutineContext.ensureActive()
            return Result.Error(NetworkError.UNKNOWN)
        }
        return responseToResult(response)
    }

    fun <T> responseToResult(
        response: Response<T>,
    ): Result<T, NetworkError> {
        return when (response.code()) {
            in 200..299 -> {
                try {
                    Result.Success(response.body()!!)
                } catch (e: SerializationException) {
                    Result.Error(NetworkError.SERIALIZATION)
                }
            }

            408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
            429 -> Result.Error(NetworkError.TOO_MANY_REQUESTS)
            in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
            else -> Result.Error(NetworkError.UNKNOWN)
        }
    }
}
