package com.morse.presentation.koin

import com.morse.presentation.viewmodel.SuperHeroViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

var viewmodelsModules = module {

    viewModel { SuperHeroViewModel(get()) }

}