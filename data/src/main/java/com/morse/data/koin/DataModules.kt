package com.morse.data.koin

import com.morse.data.repository.MarvelHerosRepositoryImpl
import com.morse.domain.repository.MarvelHerosRepositoryInterface
import org.koin.dsl.module

var dataModules = module {
    factory <MarvelHerosRepositoryInterface> { MarvelHerosRepositoryImpl() }
}