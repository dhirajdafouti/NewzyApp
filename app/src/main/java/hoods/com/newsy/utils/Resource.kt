package hoods.com.newsy.utils

sealed class Resource<T>{
    class Success<T>(val data:T):Resource<T>()
    class Error<T>(val error:Throwable?,val data:T? = null):Resource<T>()
    class Loading<T>:Resource<T>()
}
