package com.morse.marvel.koin

import com.morse.data.koin.dataModules
import com.morse.domain.koin.useCasesModule
import com.morse.lib.koin.remoteModules
import com.morse.presentation.koin.viewmodelsModules
import org.koin.core.module.Module
import org.koin.dsl.module

var appModule = arrayListOf<Module>(
        viewmodelsModules ,
        useCasesModule ,
        dataModules ,
        remoteModules
    )