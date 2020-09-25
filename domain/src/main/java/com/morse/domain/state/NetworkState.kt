package com.morse.domain.state

sealed class NetworkState<out T> {

    data class isLoading <T> (public var isLoading : Boolean) : NetworkState<T>()

    data class error<T> (public var errorType: NetworkErrorType) : NetworkState<T>()

    data class success <T> (public var result : ArrayList<T> ) : NetworkState<T>()
}