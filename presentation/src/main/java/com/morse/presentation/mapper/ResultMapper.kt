package com.morse.presentation.mapper

interface ResultMapper < in O , out T> {

    public fun performMap (input : O ) : T

}