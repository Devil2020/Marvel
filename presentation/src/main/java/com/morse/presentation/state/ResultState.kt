package com.morse.presentation.state

sealed class ResultState<out T> {
    data class isLoading <T> (public var isLoading : Boolean) : ResultState<T>()

    data class error<T> (public var errorType: ResultErrorState) : ResultState<T>()

    data class success <T> (public var result : ArrayList<T> ) : ResultState<T>()
}