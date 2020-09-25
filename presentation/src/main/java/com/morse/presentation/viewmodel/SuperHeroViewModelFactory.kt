package com.morse.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.morse.domain.usecases.GetSuperHerosInformations

class SuperHeroViewModelFactory constructor(private var useCase : GetSuperHerosInformations? ) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SuperHeroViewModel(useCase!!) as T
    }


}