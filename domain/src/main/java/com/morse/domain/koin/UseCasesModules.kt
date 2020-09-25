package com.morse.domain.koin

import com.morse.domain.repository.MarvelHerosRepositoryInterface
import com.morse.domain.usecases.GetSuperHerosInformations
import org.koin.dsl.module


var useCasesModule = module {

    factory { GetSuperHerosInformations(get<MarvelHerosRepositoryInterface>()) }

}