package com.morse.data.mapper

interface BasicMapper <in O , T>{

    public fun performMap (input : O) : T

}