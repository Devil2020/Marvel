package com.morse.lib.koin

import com.morse.remote.core.MarvelApis
import com.morse.remote.core.RetrofitBuilder
import org.koin.dsl.module

var remoteModules = module {
    single <MarvelApis>{
        RetrofitBuilder.getNetworkInteractor()
    }
}